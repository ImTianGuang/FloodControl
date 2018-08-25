package com.tian.cloud.service.service;

import com.google.common.collect.Multimap;
import com.tian.cloud.service.dao.entity.CommonType;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface CommonTypeService {

    List<CommonType> selectByType(Integer commonTypeEnum);

    Multimap<Integer, CommonType> selectAll();
}
