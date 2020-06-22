package com.zp.module.fileupload.util.impl;

import com.zp.api.fileupload.entity.UploadFileEntity;
import com.zp.common.core.exception.RRException;
import com.zp.common.core.util.DateUtil;
import com.zp.module.fileupload.util.FileUploadUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.Date;
import java.util.UUID;

public class FileUploadUtilLocalImpl implements FileUploadUtil {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private String nginx_url;
    private String rootPath;

    public FileUploadUtilLocalImpl() {

    }

    public FileUploadUtilLocalImpl(String rootPath, String nginx_url) {

        if(rootPath.endsWith("/")){
            this.rootPath = rootPath;
        }else if(rootPath.endsWith("\\")){
            this.rootPath = rootPath;
        }else{
            this.rootPath = rootPath+"/";
        }



        this.nginx_url = nginx_url.endsWith("/") ? nginx_url : nginx_url + "/";
    }


    @Override
    public String uploadFilePreviewPdf(InputStream inputStream,String suffix){
        String yyyyMM = DateUtil.formatData(new Date(),"yyyyMM");
        String path =yyyyMM+"/"+UUID.randomUUID().toString()+"."+suffix;
        File file = new File(rootPath+path);

        File parentFile = file.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);

            IOUtils.copy(inputStream,out);
        }catch (Exception e){
            e.getStackTrace();
            return null;
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return  nginx_url + path;
    }

    @Override
    public UploadFileEntity uploadFile(MultipartFile file) {
        try {
            String name = file.getOriginalFilename();

            String suffix = name.substring(name.lastIndexOf(".") + 1);

            String yyyyMM = DateUtil.formatData(new Date(),"yyyyMM");

            String path =yyyyMM+"/"+UUID.randomUUID().toString()+"."+suffix;

            File file1 = new File(rootPath+path);

            File parentFile = file1.getParentFile();
            if(!parentFile.exists()){
                parentFile.mkdirs();
            }
            file.transferTo(file1);

            UploadFileEntity uploadFileEntity = new UploadFileEntity();
            uploadFileEntity.setCreateDate(new Date());
            uploadFileEntity.setFileUrl( nginx_url +  path);
            uploadFileEntity.setRealName(name);
            uploadFileEntity.setFileSuffix(suffix);
            uploadFileEntity.setFileSize(file.getSize());
            uploadFileEntity.setFileName(path.substring(path.lastIndexOf("/")+1));
            return uploadFileEntity ;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException(e.getMessage());
        }
    }

    @Override
    public void downloadFile(UploadFileEntity entity, OutputStream out) {

    }
}
