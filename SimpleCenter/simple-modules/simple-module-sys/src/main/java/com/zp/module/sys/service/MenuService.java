package com.zp.module.sys.service;
 
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import com.zp.api.sys.entity.MenuEntity;

import java.util.List;
import java.util.Set;


/**
 * 菜单表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:26
 */
public interface MenuService extends IService<MenuEntity> {

    IPage<MenuEntity> queryPage(MenuEntity Menu, IPage<MenuEntity> page);

    Set<String> findByRoleId(String roleId);
    List<MenuEntity> tree();
}

