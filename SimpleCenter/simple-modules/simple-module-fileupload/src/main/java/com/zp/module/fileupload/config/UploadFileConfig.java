package com.zp.module.fileupload.config;

import com.zp.module.fileupload.util.FileUploadUtil;
import com.zp.module.fileupload.util.impl.FileUploadUtilFastdfsImpl;
import com.zp.module.fileupload.util.impl.FileUploadUtilLocalImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RefreshScope//需要用http://ip:port/actuator/refresh
public class UploadFileConfig {

    @Value("${upload.type}")
    private String type;

    private String path;

    private String longinx_url;


    @Value("${upload.fastdfs.tracker_server:''}")
    private String tracker_server;
    @Value("${upload.fastdfs.charset:UTF-8}")
    private String charset;
    @Value("${upload.fastdfs.http_anti_steal_token:false}")
    private boolean http_anti_steal_token;
    @Value("${upload.fastdfs.http_secret_key:123456}")
    private String http_secret_key;
    @Value("${upload.fastdfs.nginx_url}")
    private String nginx_url;








    @Bean
    public FileUploadUtil getFileUploadUtil(){
        FileUploadUtil fileUploadUtil = null;
        if (StringUtils.isBlank(type)||"fastdfs".equals(type)){//已经进行了配置
            fileUploadUtil = new FileUploadUtilFastdfsImpl(tracker_server,charset,http_anti_steal_token,http_secret_key,nginx_url);
        }else if(StringUtils.isNotBlank(type)&&"local".equals(type)){//或者本地
            fileUploadUtil = new FileUploadUtilLocalImpl(path,longinx_url);
        }
        return fileUploadUtil;
    }
}
