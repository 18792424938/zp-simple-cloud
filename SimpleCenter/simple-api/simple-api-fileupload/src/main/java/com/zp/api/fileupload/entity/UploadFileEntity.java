package com.zp.api.fileupload.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName(value = "sys_upload_file",schema = "sys")
@ApiModel("附件表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileEntity {
    /**
     * id
     */
    @TableId(type= IdType.UUID)
    @TableField("id")
    @ApiModelProperty("id")
    private String id;



    /**
     * 真实文件名
     */
    @TableField("real_name")
    @ApiModelProperty("真实文件名")
    private String realName;

    /**
     * 文件名
     */
    @TableField("file_name")
    @ApiModelProperty("文件名")
    private String fileName;

    /**
     * 文件地址
     */
    @TableField("file_url")
    @ApiModelProperty("文件地址")
    private String fileUrl;


    /**
     * 文件地址
     */
    @TableField("file_size")
    @ApiModelProperty("文件大小")
    private long fileSize;

    /**
     * 文件后缀
     */
    @TableField("file_suffix")
    @ApiModelProperty("文件后缀")
    private String fileSuffix;


    /**
     * 上传时间
     */
    @TableField("create_date")
    @ApiModelProperty("上传时间")
    private Date createDate;

    /**
     * 预览地址
     */
    @TableField("preview_url")
    @ApiModelProperty("预览地址")
    private String preview_url;

    /**
     * 10:已完成(不用转码),20:,上传完毕,30:转码中,40:已转码
     */
    @TableField("encode")
    @ApiModelProperty("10:已完成(不用转码),20:,上传完毕,30:转码中,40:已转码")
    private Integer encode;

    /**
     * 转码完成时间
     */
    @TableField("encode_date")
    @ApiModelProperty("转码完成时间")
    private Date encodeDate;

    /**
     * 上传文件产生的组
     */
    @TableField("group_name")
    @ApiModelProperty("上传文件产生的组")
    private String groupName;








}
