package com.zp.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.module.sys.dao.RoleSystemDao;
import com.zp.module.sys.service.RoleSystemService;
import javafx.print.Collation;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.zp.api.sys.entity.RoleSystemEntity;

import java.util.List;
import java.util.stream.Collectors;


@Service("roleSystemService")
public class RoleSystemServiceImpl extends ServiceImpl<RoleSystemDao, RoleSystemEntity> implements RoleSystemService {


    @Override
    public List<String> getSystemIds(String roleid) {
        QueryWrapper<RoleSystemEntity> roleSystemEntityQueryWrapper = new QueryWrapper<>();
        roleSystemEntityQueryWrapper.eq("role_id",roleid);
        List<RoleSystemEntity> roleSystemEntityList = this.list(roleSystemEntityQueryWrapper);
        List<String> collect1 = roleSystemEntityList.stream().map(p -> p.getSystemId()).collect(Collectors.toList());
        return collect1;
    }

    public IPage<RoleSystemEntity> queryPage(RoleSystemEntity RoleSystem , IPage<RoleSystemEntity> page ) {
        
        IPage<RoleSystemEntity> ipage = this.page(
                page,
                new QueryWrapper<RoleSystemEntity>()
        );

        return ipage;
    }

}
