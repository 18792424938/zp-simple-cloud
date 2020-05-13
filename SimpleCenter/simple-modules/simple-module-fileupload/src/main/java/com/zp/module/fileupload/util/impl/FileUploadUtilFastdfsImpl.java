package com.zp.module.fileupload.util.impl;

import com.zp.common.core.exception.RRException;
import com.zp.api.fileupload.entity.UploadFileEntity;
import com.zp.module.fileupload.util.FileUploadUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class FileUploadUtilFastdfsImpl implements FileUploadUtil {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String trackerServer ;
    private String storageServer ;
    private String nginxUrl ;

    public FileUploadUtilFastdfsImpl() {
    }

    public FileUploadUtilFastdfsImpl(String trackerServer, String nginxUrl) {
        this.trackerServer = trackerServer;
        this.nginxUrl = nginxUrl;
    }

    public FileUploadUtilFastdfsImpl(String trackerServer, String storageServer, String nginxUrl) {
        this.trackerServer = trackerServer;
        this.storageServer = storageServer;
        this.nginxUrl = nginxUrl;

        this.trackerServer = trackerServer;
        this.storageServer = storageServer;
        this.nginxUrl = nginxUrl.endsWith("/") ? nginxUrl : nginxUrl + "/";
        try {
            ClientGlobal.initByTrackers(trackerServer);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public UploadFileEntity uploadFile(MultipartFile file) {
        InputStream input;
        try {
            input = file.getInputStream();
            String name = file.getOriginalFilename();

            String suffix = name.substring(name.lastIndexOf(".") + 1);


            byte[] bs = IOUtils.toByteArray(input);

            // 1、把FastDFS提供的jar包添加到工程中
            // String url =
            // FastdfsUtils.class.getClassLoader().getResource("fdfs_client.conf").getPath();

            // 2、初始化全局配置。加载一个配置文件。

            // 3、创建一个TrackerClient对象。
            TrackerClient trackerClient = new TrackerClient();
            // 4、创建一个TrackerServer对象。
            TrackerServer trackerServer = trackerClient.getConnection();
            // 5、声明一个StorageServer对象，null。
            StorageServer storageServer = null ;

            // 判断配置的storageServer是否为空 不为空这里开始回填
            if(StringUtils.isNotBlank(this.storageServer)) {
                String[] ss = this.storageServer.split(":");
                String ip = ss[0];
                int port = Integer.valueOf(ss[1]);
                storageServer = new StorageServer(ip, port, 0);
            }

            // 6、获得StorageClient对象。
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            // 7、直接调用StorageClient对象方法上传文件即可。
            String[] resource = storageClient.upload_file(bs, suffix, null);


            UploadFileEntity uploadFileEntity = new UploadFileEntity();

            uploadFileEntity.setCreateDate(new Date());
            uploadFileEntity.setGroupName(resource[0]);
            uploadFileEntity.setFileUrl( nginxUrl +  resource[0]+"/"+resource[1]);

            uploadFileEntity.setRealName(name);
            uploadFileEntity.setFileSuffix(suffix);
            uploadFileEntity.setFileSize((int)file.getSize());
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
