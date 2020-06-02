 
package com.zp.module.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zp.common.config.util.PagerUtil;
import com.zp.module.job.dao.ScheduleJobLogDao;
import com.zp.module.job.entity.ScheduleJobLogEntity;
import com.zp.module.job.service.ScheduleJobLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {

	@Override
	public IPage<ScheduleJobLogEntity>  queryPage(ScheduleJobLogEntity entity , PagerUtil pageUtils) {
		IPage<ScheduleJobLogEntity> page = this.page(
				pageUtils.getPage(ScheduleJobLogEntity.class),
				new QueryWrapper<ScheduleJobLogEntity>()
						.like(StringUtils.isNotBlank(entity.getBeanName()),"bean_name", entity.getBeanName())
						.like(StringUtils.isNotBlank(entity.getMethodName()),"method_name", entity.getMethodName())
						.orderByDesc("create_time")
		);

		return page;
	}

}
