package com.tian.cloud.service.service;

import com.tian.cloud.service.enums.UploadType;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface UploadService {

    boolean upload(MultipartFile file, String extraData) throws Exception;

    boolean upload(MultipartFile file, Integer uploadType, Integer refId) throws Exception;

    String encryptUploadExtra(UploadType uploadType, Integer refId) throws Exception;

    String encryptDownloadExtra(UploadType uploadType, Integer refId) throws Exception;

    String getFilePathByType(UploadType uploadType, Integer refId);

    File getFileByExt(String ext) throws Exception;
}
