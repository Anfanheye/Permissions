package com.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.model.system.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.model.vo.SysUserQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author xmk
 * @since 2022-10-31
 */
@Repository
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    IPage<SysUser> selectPage(Page<SysUser> pageParam,@Param("vo") SysUserQueryVo sysUserQueryVo);
}
