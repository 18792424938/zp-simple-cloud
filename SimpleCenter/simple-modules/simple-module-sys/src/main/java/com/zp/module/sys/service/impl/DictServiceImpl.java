package com.zp.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.common.config.util.PagerUtil;
import com.zp.module.sys.dao.DictDao;
import com.zp.module.sys.service.DictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.zp.api.sys.entity.DictEntity;



@Service("dictService")
public class DictServiceImpl extends ServiceImpl<DictDao, DictEntity> implements DictService {




    public IPage<DictEntity> queryPage(DictEntity Dict, PagerUtil pagerUtil) {
        IPage<DictEntity> ipage = this.page(
                pagerUtil.getPage(DictEntity.class),
                new QueryWrapper<DictEntity>()
                .like(StringUtils.isNotBlank(Dict.getDictName()),"dict_name",Dict.getDictName())
                .like(StringUtils.isNotBlank(Dict.getValue()),"value",Dict.getValue())
                .like(StringUtils.isNotBlank(Dict.getType()),"type",Dict.getType())
                .like(StringUtils.isNotBlank(Dict.getDescription()),"description",Dict.getDescription())
                .orderByAsc("type","order_num")
                .orderByDesc("create_date")
        );
        return ipage;
    }
}
