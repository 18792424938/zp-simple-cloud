package com.zp.module.auth.event;



import org.springframework.context.ApplicationEvent;

/**
 *   登录日志监听
 * @author zp
 *
 */
public class LoginLogEvent extends ApplicationEvent {


	private static final long serialVersionUID = -8448722764259810497L;


	public LoginLogEvent(Object source) {
		super(source);
	}
}
