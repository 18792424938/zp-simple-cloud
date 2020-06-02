package com.zp.module.log.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zp.api.log.entity.LoginLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日志
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:26
 */
@Mapper
public interface LoginLogDao extends BaseMapper<LoginLogEntity> {
	
	/*推荐在此直接添加注解写sql,可读性比较好*/
	
}
