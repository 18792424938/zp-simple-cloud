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
 * 角色系统表
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@TableName(value = "sys_role_system",schema = "sys")
@ApiModel("角色系统表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleSystemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type=IdType.UUID)
	@TableField("id") 
	@ApiModelProperty("主键")
	private String id;
	/**
	 * 角色id
	 */
	@TableField("role_id") 
	@ApiModelProperty("角色id")
	private String roleId;
	/**
	 * 系统id
	 */
	@TableField("system_id") 
	@ApiModelProperty("系统id")
	private String systemId;



 
}
