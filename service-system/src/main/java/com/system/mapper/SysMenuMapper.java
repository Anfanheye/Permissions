package com.system.mapper;

import com.model.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author xmk
 * @since 2022-11-01
 */
@Repository
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    //如果userid不为1，其他类型用户，查询这个用户权限
    List<SysMenu> findListUserId(@Param("userId") String userId);
}
