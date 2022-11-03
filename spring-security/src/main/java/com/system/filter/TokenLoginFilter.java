package com.system.filter;

import com.alibaba.fastjson.JSON;
import com.common.result.Result;
import com.common.result.ResultCodeEnum;
import com.common.utils.JwtHelper;
import com.common.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.vo.LoginVo;
import com.system.custom.CustomUser;
import com.system.service.LoginLogService;
import com.system.utils.IpUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：
 *       登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 * @ClassName TokenLoginFilter
 * @Description TODO
 * @Author Ming
 * @Date 2022/11/3 17:35
 * @Version 1.0
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private RedisTemplate redisTemplate;

    private LoginLogService loginLogService;

    //构造
    public TokenLoginFilter(AuthenticationManager authenticationManager,RedisTemplate redisTemplate,LoginLogService loginLogService) {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/index/login","POST"));
        this.redisTemplate = redisTemplate;
        this.loginLogService = loginLogService;
    }

    //获取用户名和密码，认证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            LoginVo loginVo = new ObjectMapper().readValue(request.getInputStream(), LoginVo.class);
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //认证成功调用
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) throws IOException, ServletException {
        //获取认证对象
        CustomUser customUser = (CustomUser) auth.getPrincipal();

        //保存权限数据
        redisTemplate.opsForValue().set(customUser.getUsername(), JSON.toJSONString(customUser.getAuthorities()));

        //生成token
        String token = JwtHelper.createToken(customUser.getSysUser().getId(), customUser.getSysUser().getUsername());

        //记录登录日志
        loginLogService.recordLoginLog(customUser.getUsername(),1, IpUtil.getIpAddress(request),"登陆成功");

        //返回
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        ResponseUtil.out(response, Result.ok(map));
    }

    //认证失败调用
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {
        if(e.getCause() instanceof RuntimeException) {
            ResponseUtil.out(response, Result.build(null, 204, e.getMessage()));
        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.LOGIN_MOBLE_ERROR));
        }
    }
}

