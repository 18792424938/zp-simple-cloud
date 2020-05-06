package com.zp.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.module.sys.dao.MenuDao;
import com.zp.module.sys.service.MenuService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.api.sys.entity.MenuEntity;

import java.util.List;
import java.util.Set;


@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuEntity> implements MenuService {

    @Override
    public List<MenuEntity> tree() {
        return baseMapper.tree();
    }

    @Override
    public Set<String> findByRoleId(String roleId) {

        return baseMapper.findByRoleId(roleId);
    }

    public IPage<MenuEntity> queryPage(MenuEntity Menu , IPage<MenuEntity> page ) {
        
        IPage<MenuEntity> ipage = this.page(
                page,
                new QueryWrapper<MenuEntity>()
        );

        return ipage;
    }

}
