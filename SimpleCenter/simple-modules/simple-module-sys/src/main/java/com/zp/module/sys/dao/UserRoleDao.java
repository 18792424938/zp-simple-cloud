package com.zp.module.sys.dao;

import com.zp.api.sys.entity.UserRoleEntity;

import org.apache.ibatis.annotations.Mapper; 
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用户角色表
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:24
 */
@Mapper
public interface UserRoleDao extends BaseMapper<UserRoleEntity> {
	
	/*推荐在此直接添加注解写sql,可读性比较好*/
	
}
