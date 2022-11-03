package com.system.service.impl;

import com.model.system.SysUser;
import com.system.custom.CustomUser;
import com.system.service.SysMenuService;
import com.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 类描述：
 *
 * @ClassName UserDetailsServiceImpl
 * @Description TODO
 * @Author Ming
 * @Date 2022/11/3 17:29
 * @Version 1.0
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getUserInfoByUserName(username);
        if (sysUser == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        if (sysUser.getStatus() .intValue() == 0){
            throw new RuntimeException("用户已被禁用");
        }
        //根据userid查询操作权限数据
        List<String> userPermsList = sysMenuService.getUserButtonList(sysUser.getId());
        //转换security要求格式数据
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String prem:userPermsList) {
            authorities.add(new SimpleGrantedAuthority(prem.trim()));
        }
        return new CustomUser(sysUser,authorities);
    }
}
