package com.zp.module.fileupload.util;

import com.zp.api.fileupload.entity.UploadFileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

public interface FileUploadUtil extends Serializable {
    UploadFileEntity uploadFile(MultipartFile file) ;
    UploadFileEntity uploadFile(InputStream inputStream,String suffix)  throws Exception;
    void downloadFile(UploadFileEntity entity, OutputStream out);
}
