package com.zp.module.log.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zp.api.log.entity.LogEntity;
import com.zp.api.log.entity.UserLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日志
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:26
 */
@Mapper
public interface UserLogDao extends BaseMapper<UserLogEntity> {
	
	/*推荐在此直接添加注解写sql,可读性比较好*/
	
}
