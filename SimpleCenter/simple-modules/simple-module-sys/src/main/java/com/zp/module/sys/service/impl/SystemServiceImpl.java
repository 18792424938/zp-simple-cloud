package com.zp.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.api.sys.entity.MenuEntity;
import com.zp.api.sys.entity.RoleMenuEntity;
import com.zp.api.sys.entity.RoleSystemEntity;
import com.zp.common.core.util.PagerUtil;
import com.zp.module.sys.dao.SystemDao;
import com.zp.module.sys.service.MenuService;
import com.zp.module.sys.service.RoleMenuService;
import com.zp.module.sys.service.RoleSystemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.zp.api.sys.entity.SystemEntity;
import com.zp.module.sys.service.SystemService;

import java.util.List;
import java.util.stream.Collectors;


@Service("systemService")
public class SystemServiceImpl extends ServiceImpl<SystemDao, SystemEntity> implements SystemService {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleSystemService roleSystemService;

    @Autowired
    private RoleMenuService roleMenuService;


    @Override
    public List<SystemEntity> selectByUserId(String userid) {
        return baseMapper.selectByUserId(userid);
    }

    @Override
    public void removeMyById(String systemid) {


        //删除系统绑定关系
        QueryWrapper<RoleSystemEntity> roleSystemEntityQueryWrapper = new QueryWrapper<>();
        roleSystemEntityQueryWrapper.eq("system_id",systemid);
        roleSystemService.remove(roleSystemEntityQueryWrapper);

        this.removeById(systemid);
    }

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

    public IPage<SystemEntity> queryPage(SystemEntity System , PagerUtil pagerUtil) {
        
        IPage<SystemEntity> ipage = this.page(
                pagerUtil.getPage(SystemEntity.class),
                new QueryWrapper<SystemEntity>()
                .like(StringUtils.isNotBlank(System.getName()), "name",System.getName())
        );
        return ipage;
    }

}
