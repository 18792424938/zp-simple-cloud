 
package com.zp.module.job.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.common.core.util.PagerUtil;
import com.zp.common.core.util.R;
import com.zp.common.security.annotation.RequiresPermissions;
import com.zp.module.job.entity.ScheduleJobEntity;
import com.zp.module.job.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 定时任务
 *
 * @author zhaipan
 */
@RestController
@RequestMapping("/job/schedule")
public class ScheduleJobController {
	@Autowired
	private ScheduleJobService scheduleJobService;
	
	/**
	 * 定时任务列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("job:schedule")
	public R<IPage> list(ScheduleJobEntity entity , PagerUtil pageUtils){
		IPage<ScheduleJobEntity> page = scheduleJobService.queryPage(entity,pageUtils);

		return R.ok(IPage.class).setData( page);
	}
	
	/**
	 * 定时任务信息
	 */
	@GetMapping("/info/{id}")
	public R<ScheduleJobEntity> info(@PathVariable("id") Long id){
		ScheduleJobEntity schedule = scheduleJobService.getById(id);
		return R.ok(ScheduleJobEntity.class).setData( schedule);
	}
	
	/**
	 * 保存定时任务
	 */
	@PostMapping("/save")
	@RequiresPermissions("job:schedule:save")
	public R<Object> save(@RequestBody ScheduleJobEntity scheduleJob){
		scheduleJob.setCreateTime(new Date());
		scheduleJobService.save(scheduleJob);
		
		return R.ok();
	}
	
	/**
	 * 修改定时任务
	 */
	@PostMapping("/update")
	@RequiresPermissions("job:schedule:update")
	public R<Object> update(@RequestBody ScheduleJobEntity scheduleJob){

		scheduleJobService.update(scheduleJob);
		
		return R.ok();
	}
	
	/**
	 * 删除定时任务
	 */
	@PostMapping("/delete")
	@RequiresPermissions("job:schedule:delete")
	public R<Object> delete(@RequestBody Long[] jobIds){
		scheduleJobService.deleteBatch(jobIds);
		
		return R.ok();
	}
	
	/**
	 * 立即执行任务
	 */
	@PostMapping("/run")
	@RequiresPermissions("job:schedule:run")
	public R<Object> run(@RequestBody Long[] jobIds){
		scheduleJobService.run(jobIds);
		
		return R.ok();
	}
	
	/**
	 * 暂停定时任务
	 */
	@PostMapping("/pause")
	@RequiresPermissions("job:schedule:pause")
	public R<Object> pause(@RequestBody Long[] jobIds){
		scheduleJobService.pause(jobIds);
		
		return R.ok();
	}
	
	/**
	 * 恢复定时任务
	 */
	@PostMapping("/resume")
	@RequiresPermissions("job:schedule:resume")
	public R<Object> resume(@RequestBody Long[] jobIds){
		scheduleJobService.resume(jobIds);
		
		return R.ok();
	}

}
