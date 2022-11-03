package com.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.model.system.SysRole;
import com.model.vo.AssginRoleVo;
import com.model.vo.SysRoleQueryVo;

import java.util.Map;

/**
 * 类描述：
 *
 * @ClassName SysRoleService
 * @Description TODO
 * @Author Ming
 * @Date 2022/10/28 17:05
 * @Version 1.0
 */
public interface SysRoleService extends IService<SysRole>{
    //条件分页查询
    IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo);

    //获取用户角色数据
    Map<String, Object> getRoleById(String userId);

    //用户分配角色
    void doAssign(AssginRoleVo assginRoleVo);
}
