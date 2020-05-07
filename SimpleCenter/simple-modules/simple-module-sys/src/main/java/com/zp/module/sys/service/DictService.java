package com.zp.module.sys.service;
 
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


import com.zp.api.sys.entity.DictEntity;
import com.zp.common.core.util.PagerUtil;


/**
 * 字典表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:26
 */
public interface DictService extends IService<DictEntity> {

    IPage<DictEntity> queryPage(DictEntity Dict, PagerUtil pagerUtil);
}

