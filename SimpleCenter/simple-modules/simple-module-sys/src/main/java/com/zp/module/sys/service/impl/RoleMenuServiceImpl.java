package com.zp.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.api.sys.entity.RoleMenuEntity;
import com.zp.api.sys.entity.RoleSystemEntity;
import com.zp.module.sys.dao.RoleMenuDao;
import com.zp.module.sys.service.RoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenuEntity> implements RoleMenuService {


    @Override
    public List<String> getMenuIds(String roleid) {
        QueryWrapper<RoleMenuEntity> roleSystemEntityQueryWrapper = new QueryWrapper<>();
        roleSystemEntityQueryWrapper.eq("role_id",roleid);
        List<RoleMenuEntity> roleSystemEntityList = this.list(roleSystemEntityQueryWrapper);
        List<String> collect1 = roleSystemEntityList.stream().map(p -> p.getMenuId()).collect(Collectors.toList());
        return collect1;
    }

    public IPage<RoleMenuEntity> queryPage(RoleMenuEntity RoleMenu , IPage<RoleMenuEntity> page ) {
        
        IPage<RoleMenuEntity> ipage = this.page(
                page,
                new QueryWrapper<RoleMenuEntity>()
        );

        return ipage;
    }

}
