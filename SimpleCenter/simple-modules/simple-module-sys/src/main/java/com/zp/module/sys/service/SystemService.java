package com.zp.module.sys.service;
 
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


import com.zp.api.sys.entity.SystemEntity;
import com.zp.common.core.util.PagerUtil;

import java.util.List;


/**
 * 系统表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
public interface SystemService extends IService<SystemEntity> {

    IPage<SystemEntity> queryPage(SystemEntity System, PagerUtil pagerUtil);

    List<SystemEntity> treeMenu();

    void removeMyById(String id);

    List<SystemEntity> selectByUserId(String userid);





}

