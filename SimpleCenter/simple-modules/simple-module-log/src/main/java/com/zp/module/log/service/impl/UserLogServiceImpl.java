package com.zp.module.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.api.log.entity.UserLogEntity;
import com.zp.common.core.util.DateUtil;
import com.zp.common.config.util.PagerUtil;
import com.zp.module.log.dao.UserLogDao;
import com.zp.module.log.service.UserLogService;
import com.zp.module.log.util.AddressUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service("userLogService")
public class UserLogServiceImpl extends ServiceImpl<UserLogDao, UserLogEntity> implements UserLogService {




    @Override
    @Async("asyncSysLogExecutor")
    public boolean saveInfo(UserLogEntity entity) {
        String realAddressByIP = AddressUtil.getRealAddressByIP(entity.getIp());
        entity.setAddress(realAddressByIP);
        return this.save(entity);
    }

    public IPage<UserLogEntity> queryPage(UserLogEntity Log, PagerUtil pagerUtil) {
        IPage<UserLogEntity> ipage = this.page(
                pagerUtil.getPage(UserLogEntity.class),
                new QueryWrapper<UserLogEntity>()
                .like(StringUtils.isNotBlank(Log.getUsername()),"username",Log.getUsername())
                .like(StringUtils.isNotBlank(Log.getIp()),"ip",Log.getIp())
                .like(StringUtils.isNotBlank(Log.getSystem()),"system",Log.getSystem())
                .like(StringUtils.isNotBlank(Log.getMethod()),"method",Log.getMethod())
                .ge(DateUtil.parseDate(Log.getStartDate())!=null,"create_date",DateUtil.parseDate(Log.getStartDate()))
                .le(DateUtil.parseDate(Log.getEndDate())!=null,"create_date",DateUtil.parseDate(Log.getEndDate()))
                .orderByDesc("create_date")
        );
        return ipage;
    }
}
