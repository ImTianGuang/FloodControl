package com.tian.cloud.service.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum CommonTypeEnum {

    POSITION(0, "职位"),
    SITUATION(1, "汛情类型"),
    SOLUTION(2, "解决类型"),
    ;

    CommonTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    private static final Map<Integer, CommonTypeEnum> codeMap = Maps.newHashMap();

    static {
        for (CommonTypeEnum var : CommonTypeEnum.values()) {
            codeMap.put(var.getCode(), var);
        }
    }

    public static CommonTypeEnum toEnum(int code) {
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
