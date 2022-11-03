package com.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.model.system.SysMenu;
import com.model.system.SysRoleMenu;
import com.model.vo.AssginMenuVo;
import com.model.vo.RouterVo;
import com.system.exception.RException;
import com.system.mapper.SysMenuMapper;
import com.system.mapper.SysRoleMenuMapper;
import com.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.utils.MenuHelper;
import com.system.utils.RouterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author xmk
 * @since 2022-11-01
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    //菜单列表(树形)
    @Override
    public List<SysMenu> findNodes() {
        //获取所有菜单
        List<SysMenu> sysMenus = baseMapper.selectList(null);
        //所有菜单数据转换要求数据格式
        List<SysMenu> resultList = MenuHelper.bulidTree(sysMenus);
        return resultList;
    }

    //删除菜单
    @Override
    public void removeMenuById(String id) {
        //查询当前菜单下是否有子菜单
        //根据id = parentid
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0){
            throw new RException(201,"请删除子菜单");
        }else {
            baseMapper.deleteById(id);
        }
    }

    //根据角色分配菜单
    @Override
    public List<SysMenu> findMenuByRoleId(String roleId) {
        //获取所有菜单 status=1
        QueryWrapper<SysMenu> wrapperMenu = new QueryWrapper<>();
        wrapperMenu.eq("status",1);
        List<SysMenu> menuList = baseMapper.selectList(wrapperMenu);

        //根据角色id查询 角色分配过的菜单列表
        QueryWrapper<SysRoleMenu> wrapperRoleMenu = new QueryWrapper<>();
        wrapperRoleMenu.eq("role_id",roleId);
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(wrapperRoleMenu);

        //从第二步查询列表中，获取角色分配所有菜单id
        List<String> roleMenuIds = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu:roleMenus) {
            String menuId = sysRoleMenu.getMenuId();
            roleMenuIds.add(menuId);
        }
        //数据处理：isSelect 如果选中为true，否为false
        //拿着分配菜单id和所有菜单id比对，有相同的，让isSelect为true
        for (SysMenu sysMenu:menuList){
            if (roleMenuIds.contains(sysMenu.getId())){
                sysMenu.setSelect(true);
            }else {
                sysMenu.setSelect(false);
            }
        }
        //转换为树形结构，为最终显示 MenuHelper实现
        List<SysMenu> sysMenus = MenuHelper.bulidTree(menuList);
        return sysMenus;
    }

    //给角色分配菜单权限
    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        //根据角色id删除菜单权限
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",assginMenuVo.getRoleId());
        sysRoleMenuMapper.delete(wrapper);

        //遍历菜单id列表，一个一个进行添加
        List<String> menuIdList = assginMenuVo.getMenuIdList();
        for (String menuId:menuIdList) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
            sysRoleMenuMapper.insert(sysRoleMenu);
        }
    }

    //根据userid查询菜单权限值
    @Override
    public List<RouterVo> getUserMenuList(String userId) {
        //admin使唯一的超级管理员，操作所有内容
        List<SysMenu> sysMenuList = null;

        //判断userid值是1代表超级管理员，查询所有权限数据
        if ("1".equals(userId)){
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("status",1);
            wrapper.orderByAsc("sort_value");
            sysMenuList = baseMapper.selectList(wrapper);
        }else {
            //如果userid不为1，其他类型用户，查询这个用户权限
            sysMenuList = baseMapper.findListUserId(userId);
        }

        //构建树形结构
        List<SysMenu> sysMenuTreeList = MenuHelper.bulidTree(sysMenuList);

        //转换成前端路由要求格式数据
        List<RouterVo> routerVoList = RouterHelper.buildRouters(sysMenuTreeList);

        return routerVoList;
    }

    //根据userid查询按钮权限值
    @Override
    public List<String> getUserButtonList(String userId) {
        List<SysMenu> sysMenuList =null;
        //判断是否为管理员
        if ("1".equals(userId)){
            sysMenuList = baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        }else {
            sysMenuList = baseMapper.findListUserId(userId);
        }
        //sysMenuList遍历
        List<String> permissionList = new ArrayList<>();
        for (SysMenu sysMenu:sysMenuList) {
            if (sysMenu.getType() == 2){
                String perms = sysMenu.getPerms();
                permissionList.add(perms);
            }
        }
        return permissionList;
    }
}
