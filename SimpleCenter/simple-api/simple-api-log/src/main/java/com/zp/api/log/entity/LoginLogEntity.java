

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
@TableName(value="login_log",schema="log")
public class LoginLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long id;
	//用户名
	@TableField("username")
	private String username;
	//IP地址
	@TableField("ip")
	private String ip;

	/**
	 * 请求方地址
	 */
	@TableField("address")
	private String address;
	/**
	 * 登录结果 10,登录成功.20登录失败
	 */
	@TableField("status")
	private Integer status;
	/**
	 * 登录结果信息
	 */
	@TableField("info")
	private String info;

	/**
	 * 客户端操作系统
	 */
	@TableField("system")
	private String system;

	/**
	 * 浏览器
	 */
	@TableField("browser")
	private String browser;

	/**
	 * 具体版本
	 */
	@TableField("browser_version")
	private String browserVersion;


	/**
	 * 验证码
	 */
	@TableField("captcha")
	private String captcha;


	//创建时间
	@TableField("create_date")
	private Date createDate;

	@TableField(exist = false)
	private String startDate;


	@TableField(exist = false)
	private String endDate;


}
