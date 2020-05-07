package com.zp.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.api.sys.entity.RoleMenuEntity;
import com.zp.api.sys.entity.RoleSystemEntity;
import com.zp.common.core.util.PagerUtil;
import com.zp.module.sys.dao.RoleDao;
import com.zp.module.sys.service.RoleMenuService;
import com.zp.module.sys.service.RoleService;
import com.zp.module.sys.service.RoleSystemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;


import com.zp.api.sys.entity.RoleEntity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {


    @Autowired
    private RoleSystemService roleSystemService;
    @Autowired
    private RoleMenuService roleMenuService;


    @Override
    public void saveAuth(RoleEntity roleEntity) {


        this.save(roleEntity);

        List<String> systemIds = roleEntity.getSystemIds();
        List<RoleSystemEntity> roleSystemEntities = new LinkedList<>();

        for (String systemId : systemIds) {
            roleSystemEntities.add(new RoleSystemEntity(null,roleEntity.getId(),systemId));
        }

        List<String> menuIds = roleEntity.getMenuIds();
        List<RoleMenuEntity> roleMenuEntities = new LinkedList<>();
        for (String menuId : menuIds) {
            roleMenuEntities.add(new RoleMenuEntity(null,roleEntity.getId(),menuId));
        }


        if (roleSystemEntities.size()>0){
            roleSystemService.saveBatch(roleSystemEntities);
        }
        if (roleMenuEntities.size()>0){
            roleMenuService.saveBatch(roleMenuEntities);
        }



    }

    @Override
    public void removeByMyIds(List<String> list) {

        for (String roleid : list) {
            //删除系统绑定关系
            QueryWrapper<RoleSystemEntity> roleSystemEntityQueryWrapper = new QueryWrapper<>();
            roleSystemEntityQueryWrapper.eq("role_id",roleid);
            roleSystemService.remove(roleSystemEntityQueryWrapper);

            //删除菜单绑定关系
            QueryWrapper<RoleMenuEntity> roleMenuEntityQueryWrapper = new QueryWrapper<>();
            roleMenuEntityQueryWrapper.eq("role_id",roleid);
            roleMenuService.remove(roleMenuEntityQueryWrapper);
        }
        this.removeByIds(list);
    }

    @Override
    public void updateAuth(RoleEntity roleEntity) {
        this.updateById(roleEntity);

        List<String> systemIds = roleEntity.getSystemIds();

        //系统
        //删除丢失的绑定关系
        QueryWrapper<RoleSystemEntity> roleSystemEntityQueryWrapper = new QueryWrapper<>();
        roleSystemEntityQueryWrapper.eq("role_id",roleEntity.getId());
        roleSystemEntityQueryWrapper.notIn("system_id",systemIds);
        roleSystemService.remove(roleSystemEntityQueryWrapper);



        QueryWrapper<RoleSystemEntity> roleSystemEntityQueryWrappernew = new QueryWrapper<>();
        roleSystemEntityQueryWrappernew.eq("role_id",roleEntity.getId());
        List<RoleSystemEntity> roleSystemList = roleSystemService.list(roleSystemEntityQueryWrappernew);
        for (RoleSystemEntity roleSystemEntity : roleSystemList) {
            if(systemIds.contains(roleSystemEntity.getSystemId())){
                systemIds.remove(roleSystemEntity.getSystemId());
            }
        }

        //新角色和系统的关系
        List<RoleSystemEntity> roleSystemEntities = new LinkedList<>();
        for (String systemId : systemIds) {
            roleSystemEntities.add(new RoleSystemEntity(null,roleEntity.getId(),systemId));
        }
        //新增新的绑定关系
        if (roleSystemEntities.size()>0){
            roleSystemService.saveBatch(roleSystemEntities);
        }





        List<String> menuIds = roleEntity.getMenuIds();
        //菜单
        //删除丢失的绑定关系
        QueryWrapper<RoleMenuEntity> roleMenuEntityQueryWrapper = new QueryWrapper<>();
        roleMenuEntityQueryWrapper.eq("role_id",roleEntity.getId());
        roleMenuEntityQueryWrapper.notIn("menu_id",menuIds);
        roleMenuService.remove(roleMenuEntityQueryWrapper);


        QueryWrapper<RoleMenuEntity> roleMenuEntityQueryWrappernew = new QueryWrapper<>();
        roleMenuEntityQueryWrappernew.eq("role_id",roleEntity.getId());
        List<RoleMenuEntity> roleMenuEntitieList = roleMenuService.list(roleMenuEntityQueryWrappernew);
        for (RoleMenuEntity roleMenuEntity : roleMenuEntitieList) {
            if(menuIds.contains(roleMenuEntity.getMenuId())){
                menuIds.remove(roleMenuEntity.getMenuId());
            }
        }

        //新增新的绑定关系
        List<RoleMenuEntity> roleMenuEntities = new LinkedList<>();
        for (String menuId : menuIds) {
            roleMenuEntities.add(new RoleMenuEntity(null,roleEntity.getId(),menuId));
        }
        if (roleMenuEntities.size()>0){
            roleMenuService.saveBatch(roleMenuEntities);
        }
    }

    public IPage<RoleEntity> queryPage(RoleEntity Role , PagerUtil pagerUtil ) {
        
        IPage<RoleEntity> ipage = this.page(
                pagerUtil.getPage(RoleEntity.class),
                new QueryWrapper<RoleEntity>()
                        .like(StringUtils.isNotBlank(Role.getName()),"name",Role.getName())
        );

        return ipage;
    }

}
