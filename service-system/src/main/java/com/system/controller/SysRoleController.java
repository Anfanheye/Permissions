package com.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.result.Result;
import com.model.system.SysRole;
import com.model.vo.AssginRoleVo;
import com.model.vo.SysRoleQueryVo;
import com.system.annotation.Log;
import com.system.enums.BusinessType;
import com.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * 类描述：
 *
 * @ClassName SysRoleController
 * @Description TODO
 * @Author Ming
 * @Date 2022/10/28 17:26
 * @Version 1.0
 */
@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/admin/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation("获取用户角色数据")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable String userId){
        Map<String,Object> roleMap = sysRoleService.getRoleById(userId);
        return Result.ok(roleMap);
    }

    @ApiOperation("用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo){
        sysRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }

    //1.查询所有
    @ApiOperation("查询所有接口")
    @GetMapping("/findAll")
    public Result findAll(){
        //调用service
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }

    //2.逻辑删除接口
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("逻辑删除接口")
    @DeleteMapping("/remove/{id}")
    public Result removeRole(@PathVariable Long id){
        //调用方法
        boolean isSuccess = sysRoleService.removeById(id);
        if (isSuccess){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //3.条件分页查询
    //page当前页，limit每页记录数
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result findPageQueryRole(@PathVariable Long page,
                                    @PathVariable Long limit,
                                    SysRoleQueryVo sysRoleQueryVo){
        //创建page对象
        Page<SysRole> pageParam = new Page<>(page,limit);
        //调用service方法
        IPage<SysRole> pageModel = sysRoleService.selectPage(pageParam,sysRoleQueryVo);
        return Result.ok(pageModel);
    }

    //4.添加接口
    //@RequestBody 不能使用get提交方式
    //传递json格式数据，把json格式数据封装到对象里面{...}
    @Log(title = "角色管理",businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @ApiOperation("添加角色")
    @PostMapping("/save")
    public Result saveRole(@RequestBody SysRole sysRole){
        boolean isSuccess = sysRoleService.save(sysRole);
        if (isSuccess) {
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //5.改-根据id查询
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("根据id查询")
    @PostMapping("/findRoleById/{id}")
    public Result findRoleById(@PathVariable Long id){
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }

    //6.最终修改
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation("修改角色")
    @PostMapping("/update")
    public Result updateRole(@RequestBody SysRole sysRole){
        boolean isSuccess = sysRoleService.updateById(sysRole);
        if (isSuccess) {
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //7.批量删除
    //多个id值[1,2,3]
    //json数据格式---java的list集合
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("批量删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestParam List<Long> ids){
        sysRoleService.removeByIds(ids);
        return Result.ok();
    }
}
