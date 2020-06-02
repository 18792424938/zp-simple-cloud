package com.zp.common.log.listener;


import com.zp.api.log.entity.LoginLogEntity;
import com.zp.api.log.entity.UserLogEntity;
import com.zp.api.log.service.LogOpenFeignLogService;
import com.zp.common.log.event.LogEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 日志监听
 * @author zp
 *
 */
@Component
public class LogListener {

	Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired
	private LogOpenFeignLogService logOpenFeignLogService;




	/**
	 * 10:系统日志
	 * 20:登录日志
	 */
	@EventListener
	public void onApplicationEvent(LogEvent event) {
		logger.info("监听器执行-------");
		if("10".equals(event.getType())){
			logOpenFeignLogService.saveUserLog((UserLogEntity)event.getSource());
		}else if("20".equals(event.getType())){
			logOpenFeignLogService.saveLoginLog((LoginLogEntity)event.getSource());
		}
	}


}
