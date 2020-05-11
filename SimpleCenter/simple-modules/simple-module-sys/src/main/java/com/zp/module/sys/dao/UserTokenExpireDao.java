package com.zp.module.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zp.api.sys.entity.UserTokenExpireEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户表
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@Mapper
public interface UserTokenExpireDao extends BaseMapper<UserTokenExpireEntity> {
	

}
