package com.tian.cloud.service.enums;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;

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

    private static final Set<String> ORG2_TITLE_SET = ImmutableSet.of("24小时值班", "非汛期联系", "非汛期值班", "行政办公电话", "行政办公室电话", "行政办公座机", "行政办公室座机");
    private static final Map<Integer, Orgnization> codeMap = Maps.newHashMap();

    static {
        for (Orgnization var : Orgnization.values()) {
            codeMap.put(var.getCode(), var);
        }
    }

    public static boolean isOrg2Title(String titleName) {
        for (String title : ORG2_TITLE_SET) {
            if (titleName.contains(title)) {
                return true;
            }
        }
        return false;
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
