package com.zp.common.log.annotation;

public enum SysModule {

	sys("系统中心") , job("任务调度中心") , upload("文件存储中心"), auth("认证中心"), unknow("未知");
	
	private String name ;

	private SysModule(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	
}
