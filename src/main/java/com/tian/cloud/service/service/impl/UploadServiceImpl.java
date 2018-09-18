package com.tian.cloud.service.service.impl;

import com.google.gson.Gson;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.dao.entity.FloodSituation;
import com.tian.cloud.service.dao.entity.Message;
import com.tian.cloud.service.dao.mapper.CompanyMapper;
import com.tian.cloud.service.dao.mapper.FloodUserMapper;
import com.tian.cloud.service.dao.mapper.MessageMapper;
import com.tian.cloud.service.dao.mapper.SituationMapper;
import com.tian.cloud.service.enums.UploadType;
import com.tian.cloud.service.exception.ErrorCode;
import com.tian.cloud.service.exception.InternalException;
import com.tian.cloud.service.model.UpLoadExt;
import com.tian.cloud.service.service.FileService;
import com.tian.cloud.service.service.UploadService;
import com.tian.cloud.service.util.DesUtil;
import com.tian.cloud.service.util.ParamCheckUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private SituationMapper situationMapper;

    @Resource
    private MessageMapper messageMapper;

    @Override
    public boolean upload(MultipartFile file, String extraData) throws Exception {
        String decrypt = DesUtil.decrypt(extraData, signKey);
        UpLoadExt ext = GSON.fromJson(decrypt, UpLoadExt.class);
        ParamCheckUtil.assertTrue((System.currentTimeMillis() - ext.getTimestamp()) < TEN_MINUTE, "链接已过期，请重新获取");
        UploadType uploadType = UploadType.toEnum(ext.getUploadType());
        String filePath = fileService.uploadFile(file, uploadType);
        int affectRow = 0;
        String dbFileName = "";
        if (UploadType.FLOOD_PLAN.equals(uploadType)) {
            Company dbCompany = companyMapper.selectById(ext.getRefId());
            Company company = new Company();
            company.setId(ext.getRefId());
            company.setFloodPlan(filePath);
            affectRow = companyMapper.updateSelective(company);
            dbFileName = dbCompany.getFloodPlan();
        } else if (UploadType.FLOOD.equals(uploadType)) {
            FloodSituation dbSituation = situationMapper.getById(ext.getRefId());
            FloodSituation floodSituation = new FloodSituation();
            floodSituation.setId(ext.getRefId());
            floodSituation.setAttatch(filePath);
            affectRow = situationMapper.updateSelective(floodSituation);
            dbFileName = dbSituation.getAttatch();
        } else if (UploadType.FLOOD_IMG.equals(uploadType)) {
            FloodSituation dbFloodSituation = situationMapper.getById(ext.getRefId());
            ParamCheckUtil.assertTrue(dbFloodSituation != null, "系统异常，请重新获取上传链接");
            // todo check photos num
            FloodSituation floodSituation = new FloodSituation();
            floodSituation.setId(ext.getRefId());
            floodSituation.setPhotos(dbFloodSituation.getPhotos() + ";" + filePath);
            affectRow = situationMapper.updateSelective(floodSituation);
        } else if (UploadType.NOTICE.equals(uploadType)) {
            Message dbMessage = messageMapper.getById(ext.getRefId());
            Message message = new Message();
            message.setId(ext.getRefId());
            message.setAttatch(filePath);
            affectRow = messageMapper.updateSelective(message);
            dbFileName = dbMessage.getAttatch();
        } else if (UploadType.NOTICE_IMG.equals(uploadType)) {
            Message dbMessage = messageMapper.getById(ext.getRefId());
            Message message = new Message();
            message.setId(ext.getRefId());
            message.setPhotos(dbMessage.getPhotos() + ";" + filePath);
            affectRow = messageMapper.updateSelective(message);
        } else {
            throw new InternalException(ErrorCode.PARAM_ERROR, "未知的上传类型");
        }
        ParamCheckUtil.assertTrue(affectRow > 0, "上传失败，请重试");
        if (!StringUtils.isEmpty(dbFileName)) {
            fileService.backUpFile(dbFileName, uploadType);
        }
        return true;
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
