package com.zp.module.sys.dao;

import com.zp.api.sys.entity.DictEntity;

import org.apache.ibatis.annotations.Mapper; 
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 字典表
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:26
 */
@Mapper
public interface DictDao extends BaseMapper<DictEntity> {
	
	/*推荐在此直接添加注解写sql,可读性比较好*/
	
}
