package com.zp.module.sys.service;
 
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


import com.zp.api.sys.entity.RoleEntity;
import com.zp.common.core.util.PagerUtil;

import java.util.List;


/**
 * 角色表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
public interface RoleService extends IService<RoleEntity> {

    IPage<RoleEntity> queryPage(RoleEntity Role, PagerUtil pagerUtil);
    void saveAuth(RoleEntity roleEntity);
    void updateAuth(RoleEntity roleEntity);
    void removeByMyIds(List<String> list);
}

