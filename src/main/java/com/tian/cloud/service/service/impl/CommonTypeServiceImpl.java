package com.tian.cloud.service.service.impl;

import com.google.common.collect.*;
import com.tian.cloud.service.dao.entity.CommonType;
import com.tian.cloud.service.dao.mapper.CommonTypeMapper;
import com.tian.cloud.service.enums.LineStatusEnum;
import com.tian.cloud.service.service.CommonTypeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
        return commonTypeMapper.selectByTypeAndStatus(commonTypeEnum, LineStatusEnum.USABLE.getCode());
    }

    @Override
    public Multimap<Integer, CommonType> selectAll() {
        List<CommonType> commonTypeList = commonTypeMapper.selectAllByStatus(LineStatusEnum.USABLE.getCode());
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
                saveList.add(commonType);
            } else {
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
