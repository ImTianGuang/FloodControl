package com.tian.cloud.service.service.impl;

import com.google.gson.Gson;
import com.tian.cloud.service.config.UploadConfig;
import com.tian.cloud.service.enums.UploadType;
import com.tian.cloud.service.model.UpLoadExt;
import com.tian.cloud.service.service.FileService;
import com.tian.cloud.service.util.DesUtil;
import com.tian.cloud.service.util.ParamCheckUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author tianguang
 * 2018/9/17 下午3:17
 **/
@Service
public class FileServiceImpl implements FileService {

    private static final long TEN_MINUTE = 10 * 60 * 1000;

    private static final String signKey = "234rfasdfasdf32r";

    private static final Gson GSON = new Gson();

    @Resource
    private UploadConfig uploadConfig;

    @Override
    public String encryptExtra(UploadType uploadType, Integer refId) throws Exception {
        UpLoadExt upLoadExt = new UpLoadExt();
        upLoadExt.setRefId(refId);
        upLoadExt.setUploadType(uploadType.getCode());
        upLoadExt.setTimestamp(System.currentTimeMillis());
        return DesUtil.encrypt(GSON.toJson(upLoadExt), signKey);
    }

    @Override
    public String uploadFile(MultipartFile file, String extraData) throws Exception {
        String decrypt = DesUtil.decrypt(extraData, signKey);
        UpLoadExt upLoadExt = GSON.fromJson(decrypt, UpLoadExt.class);
        ParamCheckUtil.assertTrue((System.currentTimeMillis() - upLoadExt.getTimestamp()) <= TEN_MINUTE, "上传链接已过期,请重新获取");

        return null;
    }
}
