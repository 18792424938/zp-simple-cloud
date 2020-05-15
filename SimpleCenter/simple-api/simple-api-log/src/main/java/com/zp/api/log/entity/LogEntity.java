

package com.zp.api.log.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.text.ParseException;
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
@TableName(value="sys_log",schema="log")
public class LogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;
	//用户名
	@TableField("username")
	private String username;
	//请求方法
	private String method;
	//请求参数
	private String params;
	//执行时长(毫秒)
	private Long time;
	//IP地址
	private String ip;
	//创建时间
	@TableField("create_date")
	private Date createDate;
	// 归属系统
	private String system ;


	@TableField(exist = false)
	private String startDate;


	@TableField(exist = false)
	private String endDate;



	
}
