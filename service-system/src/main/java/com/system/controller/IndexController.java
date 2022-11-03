package com.system.controller;

import com.common.result.Result;
import com.common.utils.JwtHelper;
import com.common.utils.MD5;
import com.model.system.SysUser;
import com.model.vo.LoginVo;
import com.system.exception.RException;
import com.system.service.SysUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：
 *
 * @ClassName IndexController
 * @Description TODO
 * @Author Ming
 * @Date 2022/10/30 11:17
 * @Version 1.0
 */

@Api(tags = "用户登录接口")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {
    @Autowired
    private SysUserService sysUserService;
    //login
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){
        //根据username查看数据库
        SysUser sysUser = sysUserService.getUserInfoByUserName(loginVo.getUsername());
        //如果查询为空
        if (sysUser == null){
            throw new RException(20001,"用户不存在");
        }
        //判断密码没是否一致
        String password = loginVo.getPassword();
        String md5Password = MD5.encrypt(password);
        if (!sysUser.getPassword().equals(md5Password)){
            throw new RException(20001,"密码不正确");
        }
        //判断当前用户是否可用
        if (sysUser.getStatus().intValue()==0){
            throw new RException(20001,"用户已被禁用");
        }
        //根据userid和username生成token字符串，通过maap返回
        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        return Result.ok(map);
    }

    //info
    //roles: ["admin"], introduction: "I am a super administrator",…}
    //avatar:"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
    //name: "Super Admin"
    @GetMapping("/info")
    public Result info(HttpServletRequest request){
        //获取请求token字符串
        String token = request.getHeader("token");

        //从token字符串获取用户名称（id）
        String username = JwtHelper.getUsername(token);

        //根据用户名称获取用户信息（基本信息 菜单权限 按钮权限数据）
        Map<String,Object> map = sysUserService.getUserInfo(username);

        return Result.ok(map);
    }

    //logout
    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }
}
