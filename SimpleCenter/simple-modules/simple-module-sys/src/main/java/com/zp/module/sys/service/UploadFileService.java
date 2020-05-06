package com.zp.module.sys.service;
 
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


import com.zp.api.sys.entity.UploadFileEntity;
 

/**
 * 附件表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
public interface UploadFileService extends IService<UploadFileEntity> {

    IPage<UploadFileEntity> queryPage(UploadFileEntity UploadFile, IPage<UploadFileEntity> page);
}

