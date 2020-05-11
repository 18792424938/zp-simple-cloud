package com.zp.api.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户表
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@TableName(value = "sys_user_token_expire",schema = "sys")
@ApiModel("用户登录失效表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenExpireEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type=IdType.UUID)
	@TableField("id")
	@ApiModelProperty("id")
	private String id;

	/**
	 * 用户id
	 */
	@TableField("user_id")
	@ApiModelProperty("用户id")
	private String userId;

	/**
	 * 超时日期
	 */
	@TableField("expire_date")
	@ApiModelProperty("超时日期")
	private Date expireDate;


 
}
