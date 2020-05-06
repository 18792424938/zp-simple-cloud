package com.zp.module.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zp.api.sys.entity.RoleMenuEntity;
import com.zp.api.sys.entity.RoleSystemEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色系统表
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@Mapper
public interface RoleMenuDao extends BaseMapper<RoleMenuEntity> {
	
	/*推荐在此直接添加注解写sql,可读性比较好*/
	
}
