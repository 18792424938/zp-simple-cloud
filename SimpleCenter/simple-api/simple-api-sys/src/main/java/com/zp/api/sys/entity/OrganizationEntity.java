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
 * 角色表
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@TableName(value = "sys_organization",schema = "sys")
@ApiModel("角色表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 创建日期
	 */
	@TableField("create_date") 
	@ApiModelProperty("创建日期")
	private Date createDate;
	/**
	 * 创建人
	 */
	@TableField("create_id") 
	@ApiModelProperty("创建人")
	private String createId;
	/**
	 * 描述
	 */
	@TableField("describe") 
	@ApiModelProperty("描述")
	private String describe;
	/**
	 * id
	 */
	@TableId(type=IdType.UUID)
	@TableField("id") 
	@ApiModelProperty("id")
	private String id;
	/**
	 * 名称
	 */
	@TableField("name") 
	@ApiModelProperty("名称")
	private String name;
	/**
	 * 父id
	 */
	@TableField("parent_id") 
	@ApiModelProperty("父id")
	private String parentId;


	/**
	 * 修改日期
	 */
	@TableField("update_date") 
	@ApiModelProperty("修改日期")
	private Date updateDate;
	/**
	 * 修改人
	 */
	@TableField("update_id") 
	@ApiModelProperty("修改人")
	private String updateId;




	/**
	 * 父id名称
	 */
	@TableField(exist = false)
	@ApiModelProperty("父id名称")
	private String parentName;


	/**
	 * 组织结构子集
	 */
	@TableField(exist = false)
	@ApiModelProperty("组织结构子集")
	private List<OrganizationEntity> children;

}
