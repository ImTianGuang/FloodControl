package com.tian.cloud.service.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum SituationTargetEnum {

    SITUATION(0, "汛清"),
    SOLUTION(1, "措施"),;

    SituationTargetEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    private static final Map<Integer, SituationTargetEnum> codeMap = Maps.newHashMap();

    static {
        for (SituationTargetEnum var : SituationTargetEnum.values()) {
            codeMap.put(var.getCode(), var);
        }
    }

    public static SituationTargetEnum toEnum(int code) {
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
