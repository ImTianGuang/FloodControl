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
import com.tian.cloud.service.model.DownloadExt;
import com.tian.cloud.service.model.UpLoadExt;
import com.tian.cloud.service.service.FileService;
import com.tian.cloud.service.service.UploadService;
import com.tian.cloud.service.util.DesUtil;
import com.tian.cloud.service.util.ParamCheckUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

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
        Integer refId = ext.getRefId();
        doUpload(file, uploadType, refId);
        return true;
    }

    private void doUpload(MultipartFile file, UploadType uploadType, Integer refId) throws Exception {
        ParamCheckUtil.assertTrue(refId!=null, "请选择要上传的单位");
        String filePath = fileService.uploadFile(file, uploadType);
        int affectRow = 0;
        String dbFileName = "";
        if (UploadType.FLOOD_PLAN.equals(uploadType)) {
            Company dbCompany = companyMapper.selectById(refId);
            Company company = new Company();
            company.setId(refId);
            company.setFloodPlan(filePath);
            affectRow = companyMapper.updateSelective(company);
            dbFileName = dbCompany.getFloodPlan();
        } else if (UploadType.FLOOD.equals(uploadType)) {
            FloodSituation dbSituation = situationMapper.getById(refId);
            FloodSituation floodSituation = new FloodSituation();
            floodSituation.setId(refId);
            floodSituation.setAttatch(filePath);
            floodSituation.setUpdateTime(System.currentTimeMillis());
            affectRow = situationMapper.updateSelective(floodSituation);
            dbFileName = dbSituation.getAttatch();
        } else if (UploadType.FLOOD_IMG.equals(uploadType)) {
            FloodSituation dbFloodSituation = situationMapper.getById(refId);
            ParamCheckUtil.assertTrue(dbFloodSituation != null, "系统异常，请重新获取上传链接");
            // todo check photos num
            FloodSituation floodSituation = new FloodSituation();
            floodSituation.setId(refId);
            floodSituation.setPhotos(dbFloodSituation.getPhotos() + ";" + filePath);
            floodSituation.setUpdateTime(System.currentTimeMillis());
            affectRow = situationMapper.updateSelective(floodSituation);
        } else if (UploadType.NOTICE.equals(uploadType)) {
            Message dbMessage = messageMapper.getById(refId);
            Message message = new Message();
            message.setId(refId);
            message.setAttatch(filePath);
            message.setUpdateTime(System.currentTimeMillis());
            affectRow = messageMapper.updateSelective(message);
            dbFileName = dbMessage.getAttatch();
        } else if (UploadType.NOTICE_IMG.equals(uploadType)) {
            Message dbMessage = messageMapper.getById(refId);
            Message message = new Message();
            message.setId(refId);
            message.setPhotos(dbMessage.getPhotos() + ";" + filePath);
            message.setUpdateTime(System.currentTimeMillis());
            affectRow = messageMapper.updateSelective(message);
        } else if (UploadType.FLOOD_SUM.equals(uploadType)) {
            Company dbCompany = companyMapper.selectById(refId);
            Company company = new Company();
            company.setId(refId);
            company.setFloodSum(filePath);
            affectRow = companyMapper.updateSelective(company);
            dbFileName = dbCompany.getFloodPlan();
        }else {
            throw new InternalException(ErrorCode.PARAM_ERROR, "未知的上传类型");
        }
        ParamCheckUtil.assertTrue(affectRow > 0, "上传失败，请重试");
        if (!StringUtils.isEmpty(dbFileName)) {
            fileService.backUpFile(dbFileName, uploadType);
        }
    }

    @Override
    public boolean upload(MultipartFile file, Integer uploadType, Integer refId) throws Exception {
        doUpload(file, UploadType.toEnum(uploadType), refId);
        return true;
    }

    @Override
    public String getFilePathByType(UploadType uploadType, Integer refId) {
        if (UploadType.FLOOD_PLAN == uploadType) {
            Company company = companyMapper.selectById(refId);
            return company.getFloodPlan();
        }
        if (UploadType.FLOOD == uploadType) {
            FloodSituation floodSituation = situationMapper.getById(refId);
            return floodSituation.getAttatch();
        }
        if (UploadType.NOTICE == uploadType) {
            Message message = messageMapper.getById(refId);
            return message.getAttatch();
        }
        return "";
    }

    @Override
    public File getFileByExt(String ext) throws Exception {
        String decrypt = DesUtil.decrypt(ext, signKey);
        UpLoadExt downloadExt = GSON.fromJson(decrypt, UpLoadExt.class);
        ParamCheckUtil.assertTrue((System.currentTimeMillis() - downloadExt.getTimestamp()) < TEN_MINUTE, "链接已过期，请重新获取");
        String filePath = this.getFilePathByType(UploadType.toEnum(downloadExt.getUploadType()), downloadExt.getRefId());
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }
        return new File(filePath);
    }

    @Override
    public String encryptDownloadExtra(UploadType uploadType, Integer refId) throws Exception {
        return encryptUploadExtra(uploadType, refId);
    }

    @Override
    public String encryptUploadExtra(UploadType uploadType, Integer refId) throws Exception {
        UpLoadExt upLoadExt = new UpLoadExt();
        upLoadExt.setRefId(refId);
        upLoadExt.setUploadType(uploadType.getCode());
        upLoadExt.setTimestamp(System.currentTimeMillis());
        return DesUtil.encrypt(GSON.toJson(upLoadExt), signKey);
    }
}
