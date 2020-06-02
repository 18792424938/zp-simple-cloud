package com.zp.common.log.event;



import org.springframework.context.ApplicationEvent;

/**
 *  告警信息
 * @author zp
 *
 */
public class LogEvent extends ApplicationEvent {


	private static final long serialVersionUID = -8448722764259810497L;

	/**
	 * 10:系统日志
	 * 20:登录日志
	 */
	private String type;


	public LogEvent(Object source,String type) {
		super(source);
		this.type = type;
	}


	public String getType() {
		return type;
	}
}
