package com.zp.module.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zp.api.sys.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@Mapper
public interface LoginDao extends BaseMapper<UserEntity> {
	

	
}
