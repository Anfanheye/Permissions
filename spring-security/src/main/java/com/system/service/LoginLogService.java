package com.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.model.system.SysLoginLog;
import com.model.vo.SysLoginLogQueryVo;

/**
 * 类描述：
 *
 * @ClassName LoginLogService
 * @Description TODO
 * @Author Ming
 * @Date 2022/11/3 20:01
 * @Version 1.0
 */
public interface LoginLogService {

    //登录日志
    public void recordLoginLog(String username,Integer status,String ipaddr,String message);

    //条件分页查询登录日志
    IPage<SysLoginLog> selectPage(long page, long limit, SysLoginLogQueryVo sysLoginLogQueryVo);

}
