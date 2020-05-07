package com.zp.api.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;




import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字典表
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:26
 */
@TableName(value = "sys_dict",schema = "sys")
@ApiModel("字典表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictEntity implements Serializable {
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
	@TableField("description") 
	@ApiModelProperty("描述")
	private String description;
	/**
	 * 名称
	 */
	@TableField("dict_name") 
	@ApiModelProperty("名称")
	private String dictName;
	/**
	 * 主键
	 */
	@TableId(type=IdType.UUID)
	@TableField("id") 
	@ApiModelProperty("主键")
	private String id;
	/**
	 * 数字越大越靠前
	 */
	@TableField("order_num") 
	@ApiModelProperty("数字越大越靠前")
	private Integer orderNum;
	/**
	 * run_status10,启用,20禁用
	 */
	@TableField("status") 
	@ApiModelProperty("run_status10,启用,20禁用")
	private Integer status;
	/**
	 * 名称类型
	 */
	@TableField("type") 
	@ApiModelProperty("名称类型")
	private String type;
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
	 * 字典值
	 */
	@TableField("value") 
	@ApiModelProperty("字典值")
	private String value;
 
}
