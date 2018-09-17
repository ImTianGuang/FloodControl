package com.tian.cloud.service.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum UploadType {

    VAR0(0, "000"),;

    UploadType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

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
}
