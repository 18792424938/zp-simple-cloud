 
package com.zp.module.job.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.common.config.util.PagerUtil;
import com.zp.common.core.util.R;
import com.zp.common.security.annotation.RequiresPermissions;
import com.zp.module.job.entity.ScheduleJobLogEntity;
import com.zp.module.job.service.ScheduleJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务日志
 *
 * @author zhaipan
 */
@RestController
@RequestMapping("/job/scheduleLog")
public class ScheduleJobLogController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;
	
	/**
	 * 定时任务日志列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("job:schedulelog")
	public R<IPage> list(ScheduleJobLogEntity entity , PagerUtil pageUtils){
		IPage<ScheduleJobLogEntity> page = scheduleJobLogService.queryPage(entity,pageUtils);
		return R.ok(IPage.class).setData(page);
	}
	
	/**
	 * 定时任务日志信息
	 */
	@GetMapping("/info/{logId}")
	public R<ScheduleJobLogEntity> info(@PathVariable("logId") Long logId){
		ScheduleJobLogEntity log = scheduleJobLogService.getById(logId);
		return R.ok(ScheduleJobLogEntity.class).setData( log);
	}
}
