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
 * 附件表
 * 
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@TableName(value = "sys_upload_file",schema = "sys")
@ApiModel("附件表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileEntity implements Serializable {
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
	 * 文件名
	 */
	@TableField("file_name") 
	@ApiModelProperty("文件名")
	private String fileName;
	/**
	 * 文件想对路径
	 */
	@TableField("file_path") 
	@ApiModelProperty("文件想对路径")
	private String filePath;
	/**
	 * 文件大小
	 */
	@TableField("file_size") 
	@ApiModelProperty("文件大小")
	private Integer fileSize;
	/**
	 * 文件后缀
	 */
	@TableField("file_suffix") 
	@ApiModelProperty("文件后缀")
	private String fileSuffix;
	/**
	 * 文件地址
	 */
	@TableField("file_url") 
	@ApiModelProperty("文件地址")
	private String fileUrl;
	/**
	 * 主键
	 */
	@TableId(type=IdType.UUID)
	@TableField("id") 
	@ApiModelProperty("主键")
	private String id;
	/**
	 * 是否删除
	 */
	@TableField("if_delete") 
	@ApiModelProperty("是否删除")
	private Boolean ifDelete;
	/**
	 * 真实文件名
	 */
	@TableField("real_name") 
	@ApiModelProperty("真实文件名")
	private String realName;
	/**
	 * 上传类型
	 */
	@TableField("store_type") 
	@ApiModelProperty("上传类型")
	private Integer storeType;
 
}
