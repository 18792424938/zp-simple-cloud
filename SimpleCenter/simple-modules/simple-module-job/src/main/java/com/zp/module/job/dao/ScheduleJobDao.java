 
package com.zp.module.job.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zp.module.job.entity.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 定时任务
 *
 * @author zhaipan
 */
@Mapper
public interface ScheduleJobDao extends BaseMapper<ScheduleJobEntity> {
	
	/**
	 * 批量更新状态
	 */
	@Update("<script>" +
			"update qrtz.schedule_job set status = #{status} where job_id in " + 
			"		<foreach item='jobId' collection='jobIds'  open='(' separator=',' close=')'> " + 
			"			#{jobId} " + 
			"		</foreach>" + 
			"</script>") 
	int updateBatch(@Param("jobIds") Long[] jobIds, @Param("status") int status);
}
