package com.tian.cloud.service.service.impl;

import com.google.common.collect.*;
import com.tian.cloud.service.dao.entity.CommonType;
import com.tian.cloud.service.dao.mapper.CommonTypeMapper;
import com.tian.cloud.service.enums.LineStatusEnum;
import com.tian.cloud.service.service.CommonTypeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tianguang
 * 2018/8/25 下午9:00
 **/
@Service
public class CommonTypeServiceImpl implements CommonTypeService {

    @Resource
    private CommonTypeMapper commonTypeMapper;

    @Override
    public List<CommonType> selectByType(Integer commonTypeEnum) {
        List<CommonType> commonTypeList = commonTypeMapper.selectByType(commonTypeEnum);
        return commonTypeList;
    }

    @Override
    public Multimap<Integer, CommonType> selectAll() {
        List<CommonType> commonTypeList = commonTypeMapper.selectAll();
        return Multimaps.index(commonTypeList, CommonType::getCommonTypeEnum);
    }

    @Override
    public void saveOrUpdate(List<CommonType> commonTypeList) {
        if (CollectionUtils.isEmpty(commonTypeList)) {
            return;
        }
        List<CommonType> updateList = Lists.newArrayList();
        List<CommonType> saveList = Lists.newArrayList();
        for (CommonType commonType : commonTypeList) {
            if (commonType.getId() == null || commonType.getId() <= 0) {
                if (StringUtils.isEmpty(commonType.getName()) || commonType.getStatus() == LineStatusEnum.DELETED.getCode()) {
                    continue;
                }
                commonType.setCreateTime(System.currentTimeMillis());
                commonType.setUpdateTime(commonType.getCreateTime());
                saveList.add(commonType);
            } else {
                if (StringUtils.isEmpty(commonType.getName())) {
                    commonType.setName("");
                    commonType.setStatus(LineStatusEnum.DELETED.getCode());
                }
                commonType.setUpdateTime(System.currentTimeMillis());
                updateList.add(commonType);
            }
        }

        if (!CollectionUtils.isEmpty(updateList)) {
            for (CommonType commonType : commonTypeList) {
                commonTypeMapper.update(commonType);
            }
        }
        if (!CollectionUtils.isEmpty(saveList)) {
            commonTypeMapper.saveBatch(saveList);
        }
    }
}
