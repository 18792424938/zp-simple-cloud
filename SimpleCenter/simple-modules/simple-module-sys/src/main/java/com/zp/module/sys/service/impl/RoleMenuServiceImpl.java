package com.zp.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.api.sys.entity.RoleMenuEntity;
import com.zp.module.sys.dao.RoleMenuDao;
import com.zp.module.sys.service.RoleMenuService;
import org.springframework.stereotype.Service;


@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenuEntity> implements RoleMenuService {


    public IPage<RoleMenuEntity> queryPage(RoleMenuEntity RoleMenu , IPage<RoleMenuEntity> page ) {
        
        IPage<RoleMenuEntity> ipage = this.page(
                page,
                new QueryWrapper<RoleMenuEntity>()
        );

        return ipage;
    }

}
