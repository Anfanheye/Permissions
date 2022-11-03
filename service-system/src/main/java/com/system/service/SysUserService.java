package com.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.model.system.SysUser;
import com.model.vo.SysUserQueryVo;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xmk
 * @since 2022-10-31
 */
public interface SysUserService extends IService<SysUser> {
    //用户列表
    IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo);

    //更改状态
    void updateStatus(String id, Integer status);

    //根据username查询
    SysUser getUserInfoByUserName(String username);

    //根据用户名称获取用户信息（基本信息 菜单权限 按钮权限数据）
    Map<String, Object> getUserInfo(String username);
}
