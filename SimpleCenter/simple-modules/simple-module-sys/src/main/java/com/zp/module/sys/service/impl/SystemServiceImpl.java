package com.zp.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.api.sys.entity.MenuEntity;
import com.zp.module.sys.dao.SystemDao;
import com.zp.module.sys.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.zp.api.sys.entity.SystemEntity;
import com.zp.module.sys.service.SystemService;

import java.util.List;


@Service("systemService")
public class SystemServiceImpl extends ServiceImpl<SystemDao, SystemEntity> implements SystemService {

    @Autowired
    private MenuService menuService;

    @Override
    public List<SystemEntity> treeMenu() {
        QueryWrapper<SystemEntity> systemEntityQueryWrapper = new QueryWrapper<>();
        systemEntityQueryWrapper.orderByDesc("create_date");
        List<SystemEntity> systemEntityList = this.list(systemEntityQueryWrapper);
        for (SystemEntity systemEntity : systemEntityList) {
            List<MenuEntity> tree = menuService.tree(systemEntity.getId());
            systemEntity.setChildren(tree);
        }
        return systemEntityList;
    }

    public IPage<SystemEntity> queryPage(SystemEntity System , IPage<SystemEntity> page ) {
        
        IPage<SystemEntity> ipage = this.page(
                page,
                new QueryWrapper<SystemEntity>()
        );

        return ipage;
    }

}
