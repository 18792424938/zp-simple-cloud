package com.zp.common.log.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.zp.api.log.entity.UserLogEntity;
import com.zp.api.log.enums.LogEnum;
import com.zp.api.log.service.LogOpenFeignLogService;
import com.zp.api.sys.entity.UserEntity;
import com.zp.common.config.config.FeignConfig;
import com.zp.common.config.util.IPUtils;
import com.zp.common.log.annotation.SysLog;
import com.zp.common.log.event.LogEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 系统日志，切面处理类
 * 
 * @author zhaipan
 * @email  
 * @date 2017年3月8日 上午11:07:35
 */
@Aspect
@Component
public class SysUserLogAspect {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${spring.application.name:none}")
	private String system;

	@Autowired
	private ApplicationContext applicationContext;

 
	@Pointcut("@annotation(com.zp.common.log.annotation.SysLog)")
	public void logPointCut() {

	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();

		Object result= null;
		boolean runstatus = true;
		try {
			result = point.proceed();
		}catch (Exception e){
			e.getStackTrace();
			runstatus = false;
		}finally {
			//执行时长(毫秒)
			long time = System.currentTimeMillis() - beginTime;
			try {
				String resultstr = "";
				if(result!=null){
					try {
						resultstr = JSONObject.toJSONString(result);
					}catch (Exception e){
						e.getStackTrace();
					}
				}
				//保存日志
				this.saveSysLog(point,time,runstatus,resultstr);
			}catch (Exception e){
				e.getStackTrace();
			}
		}
		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time,boolean runstatus,String resultstr) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		UserLogEntity sysLog = new UserLogEntity();

		sysLog.setReturnResult(resultstr);

		sysLog.setStatus(runstatus? LogEnum.LOGSTATUS_10.getCode():LogEnum.LOGSTATUS_20.getCode());

		SysLog syslog = method.getAnnotation(SysLog.class);
		if(syslog != null){
			//注解上的描述
			sysLog.setOperation(syslog.value());
			if(syslog.system() != null)
				sysLog.setSystemName(syslog.system().getName());

		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		// 回填模块


		//请求的参数
		Object[] args = joinPoint.getArgs();
		String[] paramNames = signature.getParameterNames();
		try{

//			String params = "" ;
 			JSONObject jo = new JSONObject();
			for(int i = 0 ; i < paramNames.length && i < args.length ; i++) {
				String pname = paramNames[i];
				Object value = args[i];

				if(value instanceof MultipartFile) {
					Map<String, Object> p_map = new HashMap<>();
					MultipartFile file = (MultipartFile)value;
					p_map.put("filename", file.getOriginalFilename());
					p_map.put("file_size", file.getSize());

					value = p_map;
				}
				String tempValue = JSON.toJSONString(value) ;//value.toString();
				value = tempValue.length()>2000?tempValue.substring(0,2000):tempValue;
				jo.put(pname, value);
			}


			String params = jo.toJSONString();
			sysLog.setParams(params);
		}catch (Exception e){

		}

		//获取request
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		//设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));

		//设置请求地址
		sysLog.setMethod(request.getRequestURI());

		//获取请求方式
		sysLog.setRequestType(request.getMethod());


		Object user_ = request.getAttribute("user");

		if(user_ != null && user_ instanceof UserEntity) {
			UserEntity user = (UserEntity) user_ ;
			if(user != null) {
				sysLog.setUsername(user.getUsername());
			}
		}

		if(sysLog.getUsername() == null) {
			// 尝试获取userId
			Object NO_VALID_TOKEN = request.getAttribute(FeignConfig.NO_VALID_TOKEN);
			if(NO_VALID_TOKEN != null) {
				sysLog.setUsername(FeignConfig.NO_VALID_HEADER);
			}
		}

		sysLog.setTime(time);
		sysLog.setCreateDate(new Date());

		sysLog.setSystem(system);

		//系统日志
		LogEvent logEvent = new LogEvent(sysLog,"10");
		//保存系统日志
		applicationContext.publishEvent(logEvent);
	}
}
