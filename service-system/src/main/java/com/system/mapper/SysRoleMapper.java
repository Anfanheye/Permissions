package com.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.model.system.SysRole;
import com.model.vo.SysRoleQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 类描述：
 *
 * @ClassName SysRoleMapper
 * @Description TODO
 * @Author Ming
 * @Date 2022/10/28 14:42
 * @Version 1.0
 */
@Repository
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    //条件分页查询
    IPage<SysRole> selectPage(Page<SysRole> pageParam,@Param("vo") SysRoleQueryVo sysRoleQueryVo);
}
