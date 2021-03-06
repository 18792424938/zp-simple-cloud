 
package com.zp.module.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zp.common.config.util.PagerUtil;
import com.zp.module.job.entity.ScheduleJobLogEntity;


/**
 * 定时任务日志
 *
 * @author zhaipan
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	IPage<ScheduleJobLogEntity> queryPage(ScheduleJobLogEntity entity, PagerUtil pageUtils);
	
}
