package com.zp.module.sys.service;
 
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


import com.zp.api.sys.entity.OrganizationEntity;
 

/**
 * 角色表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
public interface OrganizationService extends IService<OrganizationEntity> {

    IPage<OrganizationEntity> queryPage(OrganizationEntity Organization, IPage<OrganizationEntity> page);
}

