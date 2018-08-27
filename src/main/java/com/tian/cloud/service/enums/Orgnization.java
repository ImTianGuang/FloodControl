package com.tian.cloud.service.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum Orgnization {

    ORG1(0, "指挥机构"),
    ORG2(1, "指挥部"),
    ;

    Orgnization(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    private static final Map<Integer, Orgnization> codeMap = Maps.newHashMap();

    static {
        for (Orgnization var : Orgnization.values()) {
            codeMap.put(var.getCode(), var);
        }
    }

    public static Orgnization toEnum(int code) {
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
