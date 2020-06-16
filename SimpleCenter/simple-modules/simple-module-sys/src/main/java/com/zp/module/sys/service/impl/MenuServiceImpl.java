package com.zp.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.api.sys.entity.RoleMenuEntity;
import com.zp.module.sys.dao.MenuDao;
import com.zp.module.sys.service.MenuService;
import com.zp.module.sys.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.api.sys.entity.MenuEntity;

import java.util.List;
import java.util.Set;


@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuEntity> implements MenuService {


    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<MenuEntity> tree(String systemId) {
        return baseMapper.tree(systemId);
    }

    @Override
    public void deleteById(String id) {

        //删除菜单与角色的绑定关系
        QueryWrapper<RoleMenuEntity> roleMenuEntityQueryWrapper = new QueryWrapper<>();
        roleMenuEntityQueryWrapper.eq("menu_id",id);
        roleMenuService.remove(roleMenuEntityQueryWrapper);

        //删除菜单
        this.removeById(id);

        //同步redis
        //@TODO
    }

    @Override
    public List<MenuEntity> nav(String systemId,String userId) {
        return baseMapper.nav(systemId,userId);
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
