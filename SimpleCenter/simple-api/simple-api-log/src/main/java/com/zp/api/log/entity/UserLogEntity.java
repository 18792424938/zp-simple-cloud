

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
@TableName(value="login_log",schema="log")
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


	/**
	 * 请求方地址
	 */
	private String address;
	/**
	 * 请求状态
	 */
	private Integer status;
	/**
	 * 请求返回的结果
	 */
	@TableField("return_result")
	private String returnResult;
	/**
	 * 请求地址
	 */
	private String path;
	/**
	 * 请求方式
	 */
	private String requestType;




	@TableField(exist = false)
	private String startDate;


	@TableField(exist = false)
	private String endDate;




}
