package com.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.model.system.SysOperLog;
import com.model.vo.SysOperLogQueryVo;
import com.system.mapper.OperLogMapper;
import com.system.service.OperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 类描述：
 *
 * @ClassName OperLogServiceImpl
 * @Description TODO
 * @Author Ming
 * @Date 2022/11/3 20:51
 * @Version 1.0
 */
@Service
public class OperLogServiceImpl implements OperLogService {

    @Autowired
    private OperLogMapper operLogMapper;

    @Override
    //操作日志保存数据库
    public void saveSysLog(SysOperLog sysOperLog) {
        operLogMapper.insert(sysOperLog);
    }

    @Override
    public IPage<SysOperLog> selectPage(Long page, Long limit, SysOperLogQueryVo sysOperLogQueryVo) {
        Page<SysOperLog> pageParam = new Page<>(page,limit);
        //获取条件值
        String title = sysOperLogQueryVo.getTitle();
        String operName = sysOperLogQueryVo.getOperName();
        String createTimeBegin = sysOperLogQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysOperLogQueryVo.getCreateTimeEnd();

        //封装参数
        QueryWrapper<SysOperLog> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)){
            wrapper.eq("title",title);
        }
        if (!StringUtils.isEmpty(operName)){
            wrapper.eq("oper_name",operName);
        }
        if (!StringUtils.isEmpty(createTimeBegin)){
            wrapper.ge("create_time",createTimeBegin);
        }
        if (!StringUtils.isEmpty(createTimeEnd)){
            wrapper.le("create_time",createTimeEnd);
        }
        //调用mapper方法实现分页条件查询
        IPage<SysOperLog> sysOperLogPage = operLogMapper.selectPage(pageParam, wrapper);
        return sysOperLogPage;
    }
}
