package com.edu.zzh.service;


import com.edu.zzh.config.UploadProperties;
import com.edu.zzh.exception.MyException;
import com.edu.zzh.exception.MyExceptionEnum;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author : zzh
 * @date : 2020/4/20 14:23
 * @describe :
 */
@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {

    @Autowired
    private FastFileStorageClient storageClient;
    @Autowired
    private UploadProperties uploadProperties;

    public String uploadImage(MultipartFile file) {
        try {
            //校验文件类型
            String contentType = file.getContentType();
            if(!uploadProperties.getAllowType().contains(contentType))
                throw new MyException(MyExceptionEnum.INVALID_FILE_TYPE);
            //校验文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image==null)
                throw new MyException(MyExceptionEnum.INVALID_FILE_TYPE);
            //上传到fastdfs
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(),".");
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            return uploadProperties.getBaseUrl()+storePath.getFullPath();
        } catch (IOException e) {
            log.error("[文件上传] 上传文件失败", e);
            throw new MyException(MyExceptionEnum.INVALID_FILE_TYPE);
        }

    }
    public String uploadVideo(MultipartFile file) {
        try {
            //上传到fastdfs
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(),".");
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            return uploadProperties.getBaseUrl()+storePath.getFullPath();
        } catch (IOException e) {
            log.error("[文件上传] 上传文件失败", e);
            throw new MyException(MyExceptionEnum.INVALID_FILE_TYPE);
        }

    }
    /**
     * 删除文件
     * @param fileUrl 文件访问地址
     * @return
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            throw new MyException(MyExceptionEnum.INVALID_FILE_TYPE);
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            log.error("[文件删除] 删除文件失败", e);
            throw new MyException(MyExceptionEnum.INVALID_FILE_TYPE);
        }
    }
}
