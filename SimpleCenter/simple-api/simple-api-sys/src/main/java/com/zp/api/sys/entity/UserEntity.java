package com.zp.api.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@TableName(value = "sys_user",schema = "sys")
@ApiModel("用户表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type=IdType.UUID)
	@TableField("id")
	@ApiModelProperty("id")
	private String id;

	/**
	 * 创建日期
	 */
	@TableField("create_date") 
	@ApiModelProperty("创建日期")
	private Date createDate;
	/**
	 * 创建id
	 */
	@TableField("create_id") 
	@ApiModelProperty("创建id")
	private String createId;

	/**
	 * logo
	 */
	@TableField("logo") 
	@ApiModelProperty("logo")
	private String logo;
	/**
	 * 组织id
	 */
	@TableField("organization_id") 
	@ApiModelProperty("组织id")
	private String organizationId;

	/**
	 * 密码
	 */
	@TableField("password") 
	@ApiModelProperty("密码")
	private String password;
	/**
	 * 姓名
	 */
	@TableField("realname") 
	@ApiModelProperty("姓名")
	private String realname;
	/**
	 * 盐
	 */
	@TableField("salt") 
	@ApiModelProperty("盐")
	private String salt;
	/**
	 * 状态,字典表user_status,10:正常,20:禁用,30:删除
	 */
	@TableField("status") 
	@ApiModelProperty("状态,字典表user_status,10:正常,20:禁用,30:删除")
	private Integer status;
	/**
	 * 修改日期
	 */
	@TableField("update_date") 
	@ApiModelProperty("修改日期")
	private Date updateDate;
	/**
	 * 修改id
	 */
	@TableField("update_id") 
	@ApiModelProperty("修改id")
	private String updateId;
	/**
	 * 账号
	 */
	@TableField("username") 
	@ApiModelProperty("账号")
	private String username;

	/**
	 * 用户的角色集合
	 */
	@TableField(exist = false)
	@ApiModelProperty("用户的角色集合")
	private List<String> roleIds;


	/**
	 * 过期时间
	 */
	@TableField(exist = false)
	@ApiModelProperty("过期时间")
	private Date expireDate;

	/**
	 * 组织名称
	 */
	@TableField(exist = false)
	@ApiModelProperty("组织名称")
	private String organizationName;





}
