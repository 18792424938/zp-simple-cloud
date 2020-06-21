package com.zp.module.fileupload.util.impl;

import com.zp.common.core.exception.RRException;
import com.zp.api.fileupload.entity.UploadFileEntity;
import com.zp.module.fileupload.util.FileUploadUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class FileUploadUtilFastdfsImpl implements FileUploadUtil {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private StorageClient1 storageClient1;
    private String nginx_url;

    public FileUploadUtilFastdfsImpl() {

    }

    public FileUploadUtilFastdfsImpl(String tracker_server,String charset,boolean http_anti_steal_token,String http_secret_key,String nginx_url) {

        try {
            ClientGlobal.initByTrackers(tracker_server);
            ClientGlobal.setG_charset(charset);
            ClientGlobal.setG_anti_steal_token(http_anti_steal_token);
            ClientGlobal.setG_secret_key(http_secret_key);
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            storageClient1 = new StorageClient1(trackerServer,storageServer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        this.nginx_url = nginx_url.endsWith("/") ? nginx_url : nginx_url + "/";
    }

    @Override
    public UploadFileEntity uploadFile(InputStream inputStream,String suffix) throws Exception {


        byte[] bs = IOUtils.toByteArray(inputStream);


        String[] resource = storageClient1.upload_file(bs, suffix, null);

        UploadFileEntity uploadFileEntity = new UploadFileEntity();

        uploadFileEntity.setPreviewUrl( nginx_url +  resource[0]+"/"+resource[1]);

        return uploadFileEntity;
    }

    @Override
    public UploadFileEntity uploadFile(MultipartFile file) {
        InputStream input;
        try {
            input = file.getInputStream();
            String name = file.getOriginalFilename();

            String suffix = name.substring(name.lastIndexOf(".") + 1);


            byte[] bs = IOUtils.toByteArray(input);

            // 7、直接调用StorageClient对象方法上传文件即可。
            String[] resource = storageClient1.upload_file(bs, suffix, null);
            UploadFileEntity uploadFileEntity = new UploadFileEntity();
            uploadFileEntity.setCreateDate(new Date());
            uploadFileEntity.setGroupName(resource[0]);
            uploadFileEntity.setFileUrl( nginx_url +  resource[0]+"/"+resource[1]);
            uploadFileEntity.setRealName(name);
            uploadFileEntity.setFileSuffix(suffix);
            uploadFileEntity.setFileSize(file.getSize());
            uploadFileEntity.setFileName(resource[1].substring(resource[1].lastIndexOf("/")+1,resource[1].length()));
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
