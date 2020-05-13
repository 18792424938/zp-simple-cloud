package com.zp.module.fileupload.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import com.zp.common.core.util.PagerUtil;
import com.zp.api.fileupload.entity.UploadFileEntity;


/**
 * 用户表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
public interface UploadFileService extends IService<UploadFileEntity> {

    IPage<UploadFileEntity> queryPage(UploadFileEntity uploadFileEntity, PagerUtil pagerUtil);

}

