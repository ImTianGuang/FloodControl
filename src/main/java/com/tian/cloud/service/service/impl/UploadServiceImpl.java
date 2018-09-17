package com.tian.cloud.service.service.impl;

import com.google.gson.Gson;
import com.tian.cloud.service.enums.UploadType;
import com.tian.cloud.service.model.UpLoadExt;
import com.tian.cloud.service.service.FileService;
import com.tian.cloud.service.service.UploadService;
import com.tian.cloud.service.util.DesUtil;
import com.tian.cloud.service.util.ParamCheckUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author tianguang
 * 2018/9/17 下午3:49
 **/
@Service
public class UploadServiceImpl implements UploadService {

    private static final long TEN_MINUTE = 10 * 60 * 1000;

    private static final String signKey = "234rfasdfasdf32r";

    private static final Gson GSON = new Gson();

    @Resource
    private FileService fileService;

    @Override
    public boolean upload(MultipartFile file, String extraData) throws Exception {
        String decrypt = DesUtil.decrypt(extraData, signKey);
        UpLoadExt ext = GSON.fromJson(decrypt, UpLoadExt.class);
        ParamCheckUtil.assertTrue((System.currentTimeMillis() - ext.getTimestamp()) < TEN_MINUTE, "链接已过期，请重新获取");
        UploadType uploadType = UploadType.toEnum(ext.getUploadType());
        String filePath = fileService.uploadFile(file, uploadType.getDirectory());

        return false;
    }

    @Override
    public String encryptExtra(UploadType uploadType, Integer refId) throws Exception {
        UpLoadExt upLoadExt = new UpLoadExt();
        upLoadExt.setRefId(refId);
        upLoadExt.setUploadType(uploadType.getCode());
        upLoadExt.setTimestamp(System.currentTimeMillis());
        return DesUtil.encrypt(GSON.toJson(upLoadExt), signKey);
    }
}
