package com.zp.module.fileupload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zp.common.core.util.PagerUtil;
import com.zp.api.fileupload.entity.UploadFileEntity;

import com.zp.module.fileupload.dao.UploadFileDao;
import com.zp.module.fileupload.service.UploadFileService;
import org.springframework.stereotype.Service;




@Service("uploadFileService")
public class UploadFileServiceImpl extends ServiceImpl<UploadFileDao, UploadFileEntity> implements UploadFileService {




    @Override
    public IPage<UploadFileEntity> queryPage(UploadFileEntity UploadFile , PagerUtil pagerUtil) {

        QueryWrapper queryWrapper = new QueryWrapper<UploadFileEntity>();

        IPage<UploadFileEntity> ipage = this.page(
                pagerUtil.getPage(UploadFileEntity.class),
                queryWrapper
        );
        return ipage;
    }

}
