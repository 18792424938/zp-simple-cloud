package com.zp.common.log.annotation;

public enum SysModule {

	sys("用户中心") , job("任务调度中心") , exam("上传中心"), auth("登录授权"), unknow("未知");
	
	private String name ;

	private SysModule(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	
}
