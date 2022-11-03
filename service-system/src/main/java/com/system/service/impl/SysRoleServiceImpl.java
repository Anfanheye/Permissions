package com.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.model.system.SysRole;
import com.model.system.SysUserRole;
import com.model.vo.AssginRoleVo;
import com.model.vo.SysRoleQueryVo;
import com.system.mapper.SysRoleMapper;
import com.system.mapper.SysUserRoleMapper;
import com.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：
 *
 * @ClassName SysRoleServiceImpl
 * @Description TODO
 * @Author Ming
 * @Date 2022/10/28 17:05
 * @Version 1.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements SysRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo) {
        IPage<SysRole> pageModel = baseMapper.selectPage(pageParam, sysRoleQueryVo);
        return pageModel;
    }

    //获取用户角色数据
    @Override
    public Map<String, Object> getRoleById(String userId) {
        //获取所有角色
        List<SysRole> roles = baseMapper.selectList(null);
        //根据用户id进行查询，已经分配角色
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<SysUserRole> userRolesList = sysUserRoleMapper.selectList(wrapper);
        //从userRoles集合中获取所有角色id
        List<String> userRoleIds = new ArrayList<>();
        for (SysUserRole userRole:userRolesList){
            String roleId = userRole.getRoleId();
            userRoleIds.add(roleId);
        }
        //封装到Map集合
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("allRoles",roles); //所有角色
        returnMap.put("userRoleIds",userRoleIds); //用户分配角色id集合
        return returnMap;

    }

    //用户分配角色
    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        //根据用户id删除已经分配角色
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",assginRoleVo.getUserId());
        sysUserRoleMapper.delete(wrapper);
        //获取所有角色id，添加角色用户关系表
        //得到角色id列表
        List<String> roleIdList = assginRoleVo.getRoleIdList();
        for (String roleId:roleIdList) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(assginRoleVo.getUserId());
            userRole.setRoleId(roleId);
            sysUserRoleMapper.insert(userRole);
        }
    }
}
