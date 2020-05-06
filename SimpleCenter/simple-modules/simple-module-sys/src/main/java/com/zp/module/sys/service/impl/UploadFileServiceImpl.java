package com.zp.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.module.sys.dao.UploadFileDao;
import com.zp.module.sys.service.UploadFileService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.zp.api.sys.entity.UploadFileEntity;



@Service("uploadFileService")
public class UploadFileServiceImpl extends ServiceImpl<UploadFileDao, UploadFileEntity> implements UploadFileService {


    public  IPage<UploadFileEntity> queryPage(UploadFileEntity UploadFile ,  IPage<UploadFileEntity> page ) {
        
        IPage<UploadFileEntity> ipage = this.page(
                page,
                new QueryWrapper<UploadFileEntity>()
        );

        return ipage;
    }

}
