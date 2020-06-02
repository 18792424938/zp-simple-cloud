 
package com.zp.module.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zp.common.config.util.PagerUtil;
import com.zp.module.job.entity.ScheduleJobEntity;

/**
 * 定时任务
 *
 * @author zhaipan
 */
public interface ScheduleJobService extends IService<ScheduleJobEntity> {

	IPage<ScheduleJobEntity> queryPage(ScheduleJobEntity entity, PagerUtil pageUtils);

	 
	
	/**
	 * 更新定时任务
	 */
	void update(ScheduleJobEntity scheduleJob);
	
	/**
	 * 批量删除定时任务
	 */
	void deleteBatch(Long[] jobIds);
	
	/**
	 * 批量更新定时任务状态
	 */
	int updateBatch(Long[] jobIds, int status);
	
	/**
	 * 立即执行
	 */
	void run(Long[] jobIds);
	
	/**
	 * 暂停运行
	 */
	void pause(Long[] jobIds);
	
	/**
	 * 恢复运行
	 */
	void resume(Long[] jobIds);
}
