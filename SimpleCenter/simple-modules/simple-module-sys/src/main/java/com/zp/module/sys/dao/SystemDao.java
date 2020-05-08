package com.zp.module.sys.dao;

import com.zp.api.sys.entity.SystemEntity;

import org.apache.ibatis.annotations.Mapper; 
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 系统表
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@Mapper
public interface SystemDao extends BaseMapper<SystemEntity> {
	
	/*推荐在此直接添加注解写sql,可读性比较好*/
    List<SystemEntity> selectByUserId(@Param("userId") String userid);
}
