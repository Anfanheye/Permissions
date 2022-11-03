package com.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.model.system.SysLoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LoginLogMapper extends BaseMapper<SysLoginLog> {
}
