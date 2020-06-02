

package com.zp.api.log.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
	@JsonFormat(shape=JsonFormat.Shape.STRING)
	private Long id;
	//用户名
	@TableField("username")
	private String username;
	//用户操作
	@TableField("operation")
	private String operation;
	//请求方法
	@TableField("method")
	private String method;
	//请求参数
	@TableField("params")
	private String params;
	//执行时长(毫秒)
	@TableField("time")
	private Long time;
	//IP地址
	@TableField("ip")
	private String ip;
	//创建时间
	@TableField("create_date")
	private Date createDate;
	// 服务名称
	@TableField("system")
	private String system ;
	//系统名称
	@TableField("system_name")
	private String systemName ;


	/**
	 * 请求方地址
	 */
	@TableField("address")
	private String address;
	/**
	 * 请求状态
	 */
	@TableField("status")
	private Integer status;
	/**
	 * 请求返回的结果
	 */
	@TableField("return_result")
	private String returnResult;
	/**
	 * 请求方式
	 */
	@TableField("request_type")
	private String requestType;






	@TableField(exist = false)
	private String startDate;


	@TableField(exist = false)
	private String endDate;




}
