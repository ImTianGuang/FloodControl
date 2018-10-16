package com.tian.cloud.service.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum CompanyGroupEnum {

    SUB_DISTRICT_OFFICE(0, "街乡镇"),
    COMMISSION_OFFICE(1, "委办局"),
    ;

    CompanyGroupEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    private static final Map<Integer, CompanyGroupEnum> codeMap = Maps.newHashMap();

    static {
        for (CompanyGroupEnum var : CompanyGroupEnum.values()) {
            codeMap.put(var.getCode(), var);
        }
    }

    public static CompanyGroupEnum toEnum(int code) {
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
