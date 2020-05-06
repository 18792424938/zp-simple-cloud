package com.zp.module.sys.dao;

import com.zp.api.sys.entity.OrganizationEntity;

import org.apache.ibatis.annotations.Mapper; 
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 角色表
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@Mapper
public interface OrganizationDao extends BaseMapper<OrganizationEntity> {
	
	/*推荐在此直接添加注解写sql,可读性比较好*/
	
}
