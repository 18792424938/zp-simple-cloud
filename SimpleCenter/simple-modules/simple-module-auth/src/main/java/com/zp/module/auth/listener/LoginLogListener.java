package com.zp.module.auth.listener;


import com.zp.api.log.entity.LoginLogEntity;
import com.zp.api.log.entity.UserLogEntity;
import com.zp.api.log.service.LogOpenFeignLogService;
import com.zp.module.auth.event.LoginLogEvent;
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
public class LoginLogListener {

	Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired
	private LogOpenFeignLogService logOpenFeignLogService;




	/**
	 * 10:系统日志
	 * 20:登录日志
	 */
	@EventListener
	public void onApplicationEvent(LoginLogEvent event) {
        logger.info("登录日志");
        logOpenFeignLogService.saveLoginLog((LoginLogEntity)event.getSource());
	}


}
