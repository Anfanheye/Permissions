package com.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.model.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OperLogMapper extends BaseMapper<SysOperLog> {
}
