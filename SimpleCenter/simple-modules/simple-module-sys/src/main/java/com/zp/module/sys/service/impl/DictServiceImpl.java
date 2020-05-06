package com.zp.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.module.sys.dao.DictDao;
import com.zp.module.sys.service.DictService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.zp.api.sys.entity.DictEntity;



@Service("dictService")
public class DictServiceImpl extends ServiceImpl<DictDao, DictEntity> implements DictService {


    public IPage<DictEntity> queryPage(DictEntity Dict, IPage<DictEntity> page) {
        IPage<DictEntity> ipage = this.page(
                page,
                new QueryWrapper<DictEntity>()
        );
        return ipage;
    }
}
