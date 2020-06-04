package com.zp.module.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.api.log.entity.LoginLogEntity;
import com.zp.common.core.util.DateUtil;
import com.zp.common.config.util.PagerUtil;

import com.zp.module.log.dao.LoginLogDao;
import com.zp.module.log.service.LoginLogService;
import com.zp.module.log.util.AddressUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service("loginLogService")
public class LoginLogServiceImpl extends ServiceImpl<LoginLogDao, LoginLogEntity> implements LoginLogService {




    @Override
    @Async("asyncLoginLogExecutor")
    public boolean saveInfo(LoginLogEntity entity) {
        String realAddressByIP = AddressUtil.getRealAddressByIP(entity.getIp());
        entity.setAddress(realAddressByIP);
        return this.save(entity);
    }

    public IPage<LoginLogEntity> queryPage(LoginLogEntity Log, PagerUtil pagerUtil) {
        IPage<LoginLogEntity> ipage = this.page(
                pagerUtil.getPage(LoginLogEntity.class),
                new QueryWrapper<LoginLogEntity>()
                .like(StringUtils.isNotBlank(Log.getUsername()),"username",Log.getUsername())
                .like(StringUtils.isNotBlank(Log.getIp()),"ip",Log.getIp())
                .like(StringUtils.isNotBlank(Log.getSystem()),"system",Log.getSystem())
                .ge(DateUtil.parseDate(Log.getStartDate())!=null,"create_date",DateUtil.parseDate(Log.getStartDate()))
                .le(DateUtil.parseDate(Log.getEndDate())!=null,"create_date",DateUtil.parseDate(Log.getEndDate()))
                .orderByDesc("create_date")
        );
        return ipage;
    }
}
