package com.tian.cloud.service.service;

import com.tian.cloud.service.enums.UploadType;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    boolean upload(MultipartFile file, String extraData) throws Exception;

    String encryptUploadExtra(UploadType uploadType, Integer refId) throws Exception;

    String encryptDownloadExtra(UploadType uploadType, Integer refId) throws Exception;

    String getFilePathByType(UploadType uploadType, Integer refId);
}
