package com.zp.module.fileupload.config;

import com.zp.module.fileupload.util.FileUploadUtil;
import com.zp.module.fileupload.util.impl.FileUploadUtilFastdfsImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class UploadFileConfig {

    @Value("${upload.type}")
    private String type;
    @Value("${upload.fastdfs.tracker_server}")
    private String tracker_server;
    @Value("${upload.fastdfs.storage_server}")
    private String storage_server;
    @Value("${upload.fastdfs.nginx_url}")
    private String nginxUrl;

    @Bean
    public FileUploadUtil getFileUploadUtil(){
        FileUploadUtil fileUploadUtil = null;
        if (StringUtils.isBlank(type)||"fastdfs".equals(type)){//已经进行了配置
            fileUploadUtil = new FileUploadUtilFastdfsImpl(tracker_server,storage_server,nginxUrl);

        }else{

        }
        return fileUploadUtil;
    }


}
