package com.tian.cloud.service.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum SituationEnum {

    VAR0(0, "000"),;

    SituationEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    private static final Map<Integer, SituationEnum> codeMap = Maps.newHashMap();

    static {
        for (SituationEnum var : SituationEnum.values()) {
            codeMap.put(var.getCode(), var);
        }
    }

    public static SituationEnum toEnum(int code) {
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
