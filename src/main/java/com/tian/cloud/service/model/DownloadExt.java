package com.tian.cloud.service.model;

import lombok.Data;

/**
 * @author tianguang
 * 2018/9/19 下午6:22
 **/
@Data
public class DownloadExt {

    private String path;

    private int uploadType;

    private Integer refId;

    private long timestamp;
}
