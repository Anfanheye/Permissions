package com.system.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.model.system.SysRole;
import com.system.mapper.SysRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * 类描述：
 *
 * @ClassName SysRoleMapperTest
 * @Description TODO
 * @Author Ming
 * @Date 2022/10/28 14:46
 * @Version 1.0
 */
@SpringBootTest
public class SysRoleMapperTest {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    //1.查询所有
    @Test
    public void findAll(){
        List<SysRole> list = sysRoleMapper.selectList(null);
        for (SysRole sysRole:list) {
            System.out.println(sysRole);
        }
    }
    //2.添加操作
    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("测试角色3");
        sysRole.setRoleCode("testManger");
        sysRole.setDescription("");
        int rows = sysRoleMapper.insert(sysRole);
        System.out.println(rows);
    }
    //3.修改操作
    @Test
    public void update(){
        //根据ID查询
        SysRole sysRole = sysRoleMapper.selectById(1);

        //设置修改值
        sysRole.setDescription("系统超级管理员");
        //调用方法实现
        sysRoleMapper.updateById(sysRole);
    }
    //4.删除操作
    @Test
    public void deleteID(){
        int i = sysRoleMapper.deleteById(9);
    }
    //5.批量删除
    @Test
    public void testBatchDelete(){
        int batchIds = sysRoleMapper.deleteBatchIds(Arrays.asList(10, 11));
    }
    //6.条件查询
    @Test
    public void seleteList(){
        //创建条件构造器
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        //设置条件
        //wrapper.eq("role_name","用户管理员");
        wrapper.like("role_name","管理员");
        //调用方法查询
        List<SysRole> list = sysRoleMapper.selectList(wrapper);
        System.out.println(list);
    }
    //条件删除
    @Test
    public void testdelete(){
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name","用户管理员");
        sysRoleMapper.delete(wrapper);
    }
}
