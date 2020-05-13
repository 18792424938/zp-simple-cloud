package com.zp.module.fileupload.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zp.api.fileupload.entity.UploadFileEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@Mapper
public interface UploadFileDao extends BaseMapper<UploadFileEntity> {
	
	/*推荐在此直接添加注解写sql,可读性比较好*/
}
