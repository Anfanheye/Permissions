package com.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.model.system.SysLoginLog;
import com.model.vo.SysLoginLogQueryVo;
import com.system.mapper.LoginLogMapper;
import com.system.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 类描述：
 *
 * @ClassName LoginLogServiceImpl
 * @Description TODO
 * @Author Ming
 * @Date 2022/11/3 20:03
 * @Version 1.0
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    //登录日志
    @Override
    public void recordLoginLog(String username, Integer status, String ipaddr, String message) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUsername(username);
        sysLoginLog.setStatus(status);
        sysLoginLog.setIpaddr(ipaddr);
        sysLoginLog.setMsg(message);
        loginLogMapper.insert(sysLoginLog);
    }

    //条件分页查询登录日志
    @Override
    public IPage<SysLoginLog> selectPage(long page, long limit, SysLoginLogQueryVo sysLoginLogQueryVo) {
        //创建page对象
        Page<SysLoginLog> pageParam = new Page(page,limit);
        //获取条件值
        String username = sysLoginLogQueryVo.getUsername();
        String createTimeBegin = sysLoginLogQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysLoginLogQueryVo.getCreateTimeEnd();

        //封装条件
        QueryWrapper<SysLoginLog> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(username)){
            wrapper.like("username",username);
        }
        if (!StringUtils.isEmpty(createTimeBegin)){
            wrapper.ge("create_time",createTimeBegin);
        }
        if (!StringUtils.isEmpty(createTimeEnd)){
            wrapper.le("create_time",createTimeEnd);
        }
        //调用mapper方法实现
        IPage<SysLoginLog> pageModel = loginLogMapper.selectPage(pageParam, wrapper);
        return pageModel;
    }
}
