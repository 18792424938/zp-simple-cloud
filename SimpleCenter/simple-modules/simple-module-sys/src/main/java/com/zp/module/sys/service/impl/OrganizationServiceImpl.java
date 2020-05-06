package com.zp.module.sys.service.impl;

import com.zp.module.sys.dao.OrganizationDao;
import com.zp.module.sys.service.OrganizationService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zp.api.sys.entity.OrganizationEntity;



@Service("organizationService")
public class OrganizationServiceImpl extends ServiceImpl<OrganizationDao, OrganizationEntity> implements OrganizationService {

    public IPage<OrganizationEntity> queryPage(OrganizationEntity Organization , IPage<OrganizationEntity> page ) {
        
        IPage<OrganizationEntity> ipage = this.page(
                page,
                new QueryWrapper<OrganizationEntity>()
        );

        return ipage;
    }

}
