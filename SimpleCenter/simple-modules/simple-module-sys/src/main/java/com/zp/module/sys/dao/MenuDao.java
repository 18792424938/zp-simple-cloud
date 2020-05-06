package com.zp.module.sys.dao;

import com.zp.api.sys.entity.MenuEntity;

import org.apache.ibatis.annotations.Mapper; 
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 菜单表
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:26
 */
@Mapper
public interface MenuDao extends BaseMapper<MenuEntity> {
	
	/*推荐在此直接添加注解写sql,可读性比较好*/
    Set<String> findByRoleId(@Param("roleId") String roleId);
    List<MenuEntity> tree();
}
