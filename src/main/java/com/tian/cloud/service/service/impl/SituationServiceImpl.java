package com.tian.cloud.service.service.impl;

import com.google.common.collect.Lists;
import com.tian.cloud.service.controller.request.CommonSearchReq;
import com.tian.cloud.service.controller.response.FloodSituationInfo;
import com.tian.cloud.service.dao.entity.CommonType;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.dao.entity.FloodSituation;
import com.tian.cloud.service.dao.entity.FloodSituationDetail;
import com.tian.cloud.service.dao.mapper.CompanyMapper;
import com.tian.cloud.service.dao.mapper.FloodSituationDetailMapper;
import com.tian.cloud.service.dao.mapper.SituationMapper;
import com.tian.cloud.service.enums.SituationTargetEnum;
import com.tian.cloud.service.service.SituationService;
import com.tian.cloud.service.util.ParamCheckUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tianguang
 * 2018/8/28 下午10:07
 **/
@Service
public class SituationServiceImpl implements SituationService {

    @Resource
    private SituationMapper situationMapper;

    @Resource
    private FloodSituationDetailMapper floodSituationDetailMapper;

    @Resource
    private CompanyMapper companyMapper;

    @Override
    public FloodSituationInfo getSituationInfo(Integer situationId) {
        FloodSituation floodSituation = situationMapper.getById(situationId);
        if (floodSituation == null) {
            return null;
        }
        Company company = companyMapper.selectById(floodSituation.getCompanyId());
        List<FloodSituationDetail> details = floodSituationDetailMapper.getBySituationId(situationId);
        List<FloodSituationDetail> situationDetailList = Lists.newArrayList();

        List<FloodSituationDetail> solutionDetailList = Lists.newArrayList();

        if (!CollectionUtils.isEmpty(details)) {
            for (FloodSituationDetail detail : details) {
                if (detail.getSituationTargetCode() == SituationTargetEnum.SITUATION.getCode()) {
                    situationDetailList.add(detail);
                }
                if (detail.getSituationTargetCode() == SituationTargetEnum.SOLUTION.getCode()) {
                    solutionDetailList.add(detail);
                }
            }
        }

        FloodSituationInfo floodSituationInfo = new FloodSituationInfo();
        floodSituationInfo.setCompany(company);
        floodSituationInfo.setFloodSituation(floodSituation);
        floodSituationInfo.setSituationDetailList(situationDetailList);
        floodSituationInfo.setSolutionDetailList(solutionDetailList);
        return floodSituationInfo;
    }

    @Override
    public void saveOrUpdate(FloodSituationInfo situationInfo) {
        ParamCheckUtil.assertTrue(situationInfo != null, "汛情信息为空");
        ParamCheckUtil.assertTrue(situationInfo.getFloodSituation() != null, "汛情信息为空");
        ParamCheckUtil.assertTrue(situationInfo.getFloodSituation().getCompanyId() != null, "未选择公司");

        if (situationInfo.getFloodSituation().getId() == null) {
            save(situationInfo);
        } else {
            update(situationInfo);
        }

    }

    @Override
    public void deleteById(int situationId) {
        situationMapper.deleteById(situationId);
        floodSituationDetailMapper.deleteByFloodSituationId(situationId);
    }

    @Override
    public List<FloodSituation> search(CommonSearchReq request) {
        return situationMapper.search(request);
    }

    private void update(FloodSituationInfo situationInfo) {
        situationMapper.update(situationInfo.getFloodSituation());
        List<FloodSituationDetail> updateList = Lists.newArrayList();
        List<FloodSituationDetail> saveList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(situationInfo.getSituationDetailList())) {
            for (FloodSituationDetail detail : situationInfo.getSituationDetailList()) {
                if (detail.getId() != null) {
                    updateList.add(detail);
                }
                if (detail.getId() == null && detail.getTargetId() != null && !StringUtils.isEmpty(detail.getTargetValue())) {
                    saveList.add(detail);
                }
            }
        }
        if (!CollectionUtils.isEmpty(situationInfo.getSolutionDetailList())) {
            for (FloodSituationDetail detail : situationInfo.getSolutionDetailList()) {
                if (detail.getId() != null) {
                    updateList.add(detail);
                }
                if (detail.getId() == null && detail.getTargetId() != null && !StringUtils.isEmpty(detail.getTargetValue())) {
                    saveList.add(detail);
                }
            }
        }

        if (!CollectionUtils.isEmpty(updateList)) {
            for (FloodSituationDetail detail : updateList) {
                floodSituationDetailMapper.update(detail);
            }
        }

        if (!CollectionUtils.isEmpty(saveList)) {
            ensureSaveField(situationInfo.getFloodSituation(), saveList);
            floodSituationDetailMapper.saveBatch(saveList);
        }
    }

    private void ensureSaveField(FloodSituation floodSituation, List<FloodSituationDetail> saveList) {
        if (CollectionUtils.isEmpty(saveList)) {
            return;
        }
        for (FloodSituationDetail detail : saveList) {
            detail.setFloodSituationId(floodSituation.getId());
            detail.setCompanyId(floodSituation.getCompanyId());
        }
    }

    private void save(FloodSituationInfo situationInfo) {
        FloodSituation floodSituation = situationInfo.getFloodSituation();
        floodSituation.setCreateTime(System.currentTimeMillis());
        floodSituation.setUpdateTime(floodSituation.getCreateTime());
        situationMapper.save(floodSituation);

        if (!CollectionUtils.isEmpty(situationInfo.getSolutionDetailList())) {
            for (FloodSituationDetail detail : situationInfo.getSolutionDetailList()) {
                detail.setFloodSituationId(floodSituation.getId());
                detail.setCreateTime(System.currentTimeMillis());
                detail.setUpdateTime(detail.getCreateTime());
            }
            floodSituationDetailMapper.saveBatch(situationInfo.getSolutionDetailList());
        }

        if (!CollectionUtils.isEmpty(situationInfo.getSituationDetailList())) {
            for (FloodSituationDetail detail : situationInfo.getSituationDetailList()) {
                detail.setFloodSituationId(floodSituation.getId());
                detail.setCreateTime(System.currentTimeMillis());
                detail.setUpdateTime(detail.getCreateTime());
            }
            floodSituationDetailMapper.saveBatch(situationInfo.getSituationDetailList());
        }
    }
}
