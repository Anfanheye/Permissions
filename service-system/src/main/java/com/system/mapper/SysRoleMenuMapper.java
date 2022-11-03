package com.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.model.system.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
}
