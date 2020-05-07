package com.zp.module.sys.dao;

import com.zp.api.sys.entity.SystemEntity;
import com.zp.api.sys.entity.UserEntity;

import org.apache.ibatis.annotations.Mapper; 
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
	
	/*推荐在此直接添加注解写sql,可读性比较好*/
    SystemEntity userSystem(@Param("userId") String userId);
}
