package com.zp.module.fileupload.listener;


import com.zp.api.fileupload.entity.UploadFileEntity;
import com.zp.api.fileupload.enums.UploadEnum;
import com.zp.module.fileupload.event.OfficeEvent;
import com.zp.module.fileupload.service.UploadFileService;
import com.zp.module.fileupload.util.FileUploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 告警监听
 * @author zp
 *
 */
@Component
public class OfficeListener {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UploadFileService uploadFileService;

	@Autowired
	private FileUploadUtil fileUploadUtil;


	@Value("${upload.encode-server}")
	private String serverUrl;



	@EventListener
	public void onApplicationEvent(OfficeEvent officeEvent) {


		logger.info("监听器执行-------");
		String id = (String)officeEvent.getSource();
		UploadFileEntity uploadFileEntity = uploadFileService.getById(id);


		//没有配置转码地址不进行转码
		if(StringUtils.isBlank(serverUrl)){
			return;
		}
		uploadFileEntity.setEncode(UploadEnum.UPLOAD_20.getCode());
		uploadFileService.updateById(uploadFileEntity);

		//开始转码
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Resource> httpEntity = new HttpEntity<Resource>(headers);

		String url = "";
		if(Arrays.asList(new String[]{"doc","docx"}).contains(uploadFileEntity.getFileSuffix().toLowerCase())){//word
			url = serverUrl.endsWith("/")?serverUrl:(serverUrl+"/")+"office/word2pdfUrlDown?url={url}";
		}else if(Arrays.asList(new String[]{"xlsx","xls"}).contains(uploadFileEntity.getFileSuffix().toLowerCase())) {//excel
			url = serverUrl.endsWith("/")?serverUrl:(serverUrl+"/")+"office/excel2pdfUrlDown";
		}else {
			return;
		}
		//放入参数
		Map<String,Object> map = new HashMap<>();
		map.put("url",uploadFileEntity.getFileUrl());

		ResponseEntity<byte[]> responseRE = restTemplate.exchange(url, HttpMethod.GET, httpEntity, byte[].class,map);

		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(responseRE.getBody());
			if(inputStream!=null){
				//获得流
				UploadFileEntity uploadFileEntityPdf = new UploadFileEntity();
				String previewPdf = fileUploadUtil.uploadFilePreviewPdf(inputStream, "pdf");
				uploadFileEntityPdf.setPreviewUrl(previewPdf);
				uploadFileEntity.setEncode(UploadEnum.UPLOAD_30.getCode());
				uploadFileEntity.setEncodeDate(new Date());
				uploadFileEntity.setPreviewUrl(uploadFileEntityPdf.getPreviewUrl());
				//转码成功
				uploadFileService.updateById(uploadFileEntity);
			}else{
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			uploadFileEntity.setEncode(UploadEnum.UPLOAD_40.getCode());
			uploadFileService.updateById(uploadFileEntity);
		}finally {
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
