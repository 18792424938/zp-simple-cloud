

package com.zp.api.log.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


/**
 * 系统日志
 * 
 * @author zhaipan
 * 
 * @date 2017-03-08 10:40:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value="sys_user_log",schema="log")
public class UserLogEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;
	//用户名
	private String username;
	//用户操作
	private String operation;
	//请求方法
	private String method;
	//请求参数
	private String params;
	//执行时长(毫秒)
	private Long time;
	//IP地址
	private String ip;
	//创建时间
	private Date createDate;
	// 服务名称
	private String system ;
	//系统名称
	private String systemName ;


	@TableField(exist = false)
	private String startDate;


	@TableField(exist = false)
	private String endDate;




}
