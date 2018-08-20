package com.tian.cloud.service.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum LineStatusEnum {

    DELETED(0, "删除"),
    USABLE(1, "有效"),
    ;

    LineStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    private static final Map<Integer, LineStatusEnum> codeMap = Maps.newHashMap();

    static {
        for (LineStatusEnum var : LineStatusEnum.values()) {
            codeMap.put(var.getCode(), var);
        }
    }

    public static LineStatusEnum toEnum(int code) {
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
