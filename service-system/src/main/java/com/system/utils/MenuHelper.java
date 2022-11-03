package com.system.utils;

import com.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 *
 * @ClassName MenuHelper
 * @Description TODO
 * @Author Ming
 * @Date 2022/11/1 17:26
 * @Version 1.0
 */
public class MenuHelper {
    //构建树形结构
    public static List<SysMenu> bulidTree(List<SysMenu> sysMenusList) {
        //创建集合封装最终数据
        List<SysMenu> trees = new ArrayList<>();
        //遍历所有菜单集合
        for (SysMenu sysMenu:sysMenusList) {
            //找到递归路口,parentid=0
            if (sysMenu.getParentId().longValue() == 0){
                trees.add(findChildren(sysMenu,sysMenusList));
            }
        }
        return trees;
    }

    //从根节点进行递归查询，查询子节点
    //判断id = parentid是否相同,如果相同则是子节点，进行数据封装
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> treeNodes) {
        //数据初始化
        sysMenu.setChildren(new ArrayList<SysMenu>());
        //遍历递归
        for (SysMenu it : treeNodes) {
            //获取当前菜单id
//            String id = sysMenu.getId();
//            long cid = Long.parseLong(id);

            //获取所有菜单parentid
//            Long parentId = it.getParentId();

            //比对
            if (Long.parseLong(sysMenu.getId())==it.getParentId()){
                if (sysMenu.getChildren() == null){
                   sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return sysMenu;
    }
}
