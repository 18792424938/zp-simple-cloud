package com.zp.api.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色表
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:24
 */
@TableName(value = "sys_user_role",schema = "sys")
@ApiModel("用户角色表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * create_date
	 */
	@TableField("create_date") 
	@ApiModelProperty("create_date")
	private Date createDate;
	/**
	 * create_id
	 */
	@TableField("create_id") 
	@ApiModelProperty("create_id")
	private String createId;
	/**
	 * id
	 */
	@TableId(type=IdType.UUID)
	@TableField("id") 
	@ApiModelProperty("id")
	private String id;
	/**
	 * role_id
	 */
	@TableField("role_id") 
	@ApiModelProperty("role_id")
	private String roleId;
	/**
	 * update_date
	 */
	@TableField("update_date") 
	@ApiModelProperty("update_date")
	private Date updateDate;
	/**
	 * update_id
	 */
	@TableField("update_id") 
	@ApiModelProperty("update_id")
	private String updateId;
	/**
	 * user_id
	 */
	@TableField("user_id") 
	@ApiModelProperty("user_id")
	private String userId;
 
}
