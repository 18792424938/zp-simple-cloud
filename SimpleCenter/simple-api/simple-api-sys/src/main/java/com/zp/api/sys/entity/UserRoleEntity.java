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
	 * id
	 */
	@TableId(type=IdType.UUID)
	@TableField("id") 
	@ApiModelProperty("id")
	private String id;

	/**
	 * user_id
	 */
	@TableField("user_id")
	@ApiModelProperty("user_id")
	private String userId;
	/**
	 * role_id
	 */
	@TableField("role_id") 
	@ApiModelProperty("role_id")
	private String roleId;

 
}
