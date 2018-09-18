package com.tian.cloud.service.service;

import com.tian.cloud.service.enums.UploadType;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadFile(MultipartFile file, UploadType uploadType) throws Exception;

    void backUpFile(String dbFileName, UploadType uploadType);
}
