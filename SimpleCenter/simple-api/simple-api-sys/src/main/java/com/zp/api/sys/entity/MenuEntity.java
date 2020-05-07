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
 * 菜单表
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:26
 */
@TableName(value = "sys_menu",schema="sys")
@ApiModel("菜单表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 组件地址
	 */
	@TableField("component_url") 
	@ApiModelProperty("组件地址")
	private String componentUrl;
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
	 * 字典表,menu_enabled,10:启用,20禁用
	 */
	@TableField("enabled") 
	@ApiModelProperty("字典表,menu_enabled,10:启用,20禁用")
	private Integer enabled;
	/**
	 * 图标
	 */
	@TableField("icon") 
	@ApiModelProperty("图标")
	private String icon;
	/**
	 * 主键
	 */
	@TableId(type=IdType.UUID)
	@TableField("id") 
	@ApiModelProperty("主键")
	private String id;
	/**
	 * 菜单名称
	 */
	@TableField("name") 
	@ApiModelProperty("菜单名称")
	private String name;
	/**
	 * 排序,数字越大越靠前
	 */
	@TableField("order_num") 
	@ApiModelProperty("排序,数字越大越靠前")
	private Integer orderNum;
	/**
	 * 父id
	 */
	@TableField("parent_id") 
	@ApiModelProperty("父id")
	private String parentId;

	@TableField(exist = false)
	@ApiModelProperty("父菜单名称")
	private String parentName;

	/**
	 * 权限码
	 */
	@TableField("perms") 
	@ApiModelProperty("权限码")
	private String perms;
	/**
	 * 路由名称
	 */
	@TableField("route_name") 
	@ApiModelProperty("路由名称")
	private String routeName;
	/**
	 * 所属系统id
	 */
	@TableField("system_id") 
	@ApiModelProperty("所属系统id")
	private String systemId;
	/**
	 * 类型字典表auth_type,10目录,20菜单,30按钮
	 */
	@TableField("type") 
	@ApiModelProperty("类型字典表auth_type,10目录,20菜单,30按钮")
	private Integer type;
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
	 * 地址栏显示路径
	 */
	@TableField("route_path")
	@ApiModelProperty("地址栏显示路径")
	private String routePath;

	/**
	 * 子菜单
	 */
	@TableField(exist = false)
	@ApiModelProperty("子菜单")
	private List<MenuEntity> children;




 
}
