package com.tian.cloud.service.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum UploadType {

    FLOOD_PLAN(0, "floodPlan", "防汛预案"),
    NOTICE(1, "notice", "通知"),
    NOTICE_IMG(2, "noticeImg", "通知图片"),
    FLOOD(3, "flood", "汛情"),
    FLOOD_IMG(4, "floodImg", "汛情图片"),
    ;

    UploadType(int code, String directory, String msg) {
        this.code = code;
        this.directory = directory;
        this.msg = msg;
    }

    private int code;

    private String directory;

    private String msg;

    private static final Map<Integer, UploadType> codeMap = Maps.newHashMap();

    static {
        for (UploadType var : UploadType.values()) {
            codeMap.put(var.getCode(), var);
        }
    }

    public static UploadType toEnum(int code) {
        return codeMap.get(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
