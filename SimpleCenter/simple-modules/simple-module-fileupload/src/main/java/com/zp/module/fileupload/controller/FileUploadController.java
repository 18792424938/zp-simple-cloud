package com.zp.module.fileupload.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zp.api.fileupload.vo.UploadFileVO;
import com.zp.common.core.exception.RRException;
import com.zp.common.core.util.R;
import com.zp.api.fileupload.entity.UploadFileEntity;

import com.zp.module.fileupload.service.UploadFileService;
import com.zp.module.fileupload.util.FileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;


@Controller
@RequestMapping("/fileupload/file")
@Api(value = "文件-通用", tags = {"文件-通用"})
public class FileUploadController {

	@Value("${upload.types}")
	private String uploadTypes;

    @Autowired
    private FileUploadUtil fileUploadUtil;

	@Autowired
	private UploadFileService uploadFileService;






	//	@SysLog(value="上传文件",system = SysModule.sys)
	@PostMapping("/upload")
	@ResponseBody
	@ApiOperation("上传文件,返回文件的最终浏览地址-PC/APP")
	@ApiResponses(value = {
			@ApiResponse(code = 0, message = "上传成功"),
			@ApiResponse(code = 1, message = "该上传文件类型不支持"),
			@ApiResponse(code = 2, message = "上传失败")
	})
	public R<Object> fileUpload(MultipartFile  file){

		if(StringUtils.isBlank(this.uploadTypes)){
			return R.error("请配置上传支持的文件类型");
		}

		// 获取文件名后缀 ,判断文件类型
		String filename = file.getOriginalFilename();

		int index = filename.lastIndexOf(".");
		String suffix = null;
		if(index >= 0) {
			suffix = filename.substring(index + 1);
		}

		if(StringUtils.isBlank(suffix)){
			return R.error("不能识别的文件类型");
		}

		if(!"*".equals(this.uploadTypes)){// 判断是否再要求得范围内
			Set<String> set = new HashSet<>();
			set.addAll(Arrays.asList(this.uploadTypes.split(",")));

			if(!set.contains(suffix)){
				return R.error("不支持["+suffix+"]类型的文件上传");
			}
		}
		//可以随意上传
        try {
            UploadFileEntity uploadFileEntity = fileUploadUtil.uploadFile(file);
			uploadFileEntity.setEncode(10);
            uploadFileService.save(uploadFileEntity);
            UploadFileVO uploadFileVO = new UploadFileVO(uploadFileEntity.getId(),uploadFileEntity.getRealName(),uploadFileEntity.getFileUrl());
            return R.ok().setData(uploadFileVO);
        }catch (RRException e){
            return R.error(e.getMsg());
        }
	}


	@PostMapping("/fileList")
	@ResponseBody
	@ApiOperation("返回数据集合")
	public R<List> fileList(@RequestBody String[] ids){

	    List<UploadFileVO> uploadFileVOList = new LinkedList<>();

		if(ids!=null&&ids.length>0){
			QueryWrapper<UploadFileEntity> queryWrapper = new QueryWrapper<>();
			queryWrapper.in("id",ids);
			queryWrapper.orderByDesc("create_date");
            List<UploadFileEntity> list = uploadFileService.list(queryWrapper);
            for (UploadFileEntity uploadFileEntity : list) {
                UploadFileVO uploadFileVO = new UploadFileVO(uploadFileEntity.getId(),uploadFileEntity.getRealName(),uploadFileEntity.getFileUrl());
                uploadFileVOList.add(uploadFileVO);
            }
		}

		return R.ok(List.class).setData(uploadFileVOList);
	}


	@GetMapping("/findById")
	@ResponseBody
	@ApiOperation("返回数据集合")
	public R<UploadFileVO> findById(String id){
		UploadFileEntity uploadFileEntity = uploadFileService.getById(id);
		UploadFileVO uploadFileVO = new UploadFileVO(uploadFileEntity.getId(),uploadFileEntity.getRealName(),uploadFileEntity.getFileUrl());
		return R.ok(UploadFileVO.class).setData(uploadFileVO);
	}




	@GetMapping("/fileDown")
	public void  fileDowload(HttpServletResponse response,HttpServletRequest request,String uuid) {
		if(StringUtils.isEmpty(uuid)) {
			return ;
		}
		//1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		response.setContentType("multipart/form-data");
		//2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
		UploadFileEntity uploadFileEntity =  uploadFileService.getById(uuid);
		// uuid 根据ID查询到文件的具体存储路径和文件名

		String name =uploadFileEntity.getRealName();



		String agent = request.getHeader("User-Agent").toUpperCase();
		try {
            // 非IE浏览器的处理：
            if (agent.indexOf("MSIE") > 0 || agent.contains("TRIDENT")|| agent.contains("EDGE")) {
                //IE
                name = URLEncoder.encode(name, "UTF-8");
            } else {
                //非IE
                name = new String(name.getBytes("UTF-8"), "ISO-8859-1");
            }
            response.setHeader("Content-disposition",String.format("attachment; filename=\"%s\"", name));
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("UTF-8");

            //通过文件路径获得File对象(假如此路径中有一个download.pdf文件)
            OutputStream os = response.getOutputStream();
            fileUploadUtil.downloadFile(uploadFileEntity, os);
        }catch (Exception e){
		    e.getStackTrace();
        }

	}



}
