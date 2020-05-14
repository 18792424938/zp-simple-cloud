 
package com.zp.module.job.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zp.module.job.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 *
 * @author zhaipan
 */
@Mapper
public interface ScheduleJobLogDao extends BaseMapper<ScheduleJobLogEntity> {
	
}
