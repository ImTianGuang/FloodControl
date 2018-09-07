package com.tian.cloud.service.model.export;

import com.google.common.base.MoreObjects;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author tianguang
 * 2018/9/6 下午8:21
 **/
@Data
public class ExportFlood {

    private String name;

    private Integer value = 0;

    private String valueContent;

    public ExportFlood(String name, String value) {
        this.name = name;
        this.addValue(value);
    }

    public void addValue(String value) {
        try {
            value = MoreObjects.firstNonNull(value, "");
            if (StringUtils.isEmpty(value)) {
                return;
            }
            Integer v = Integer.valueOf(value.trim());
            this.value = this.value + v;
        } catch (Exception e) {
            if (valueContent == null) {
                this.valueContent = value;
            } else {
                valueContent = valueContent + "+" + value;
            }
        }
    }

    public String getShowValue() {
        if (StringUtils.isEmpty(valueContent)) {
            return value.toString();
        } else {
            if (value <= 0) {
                return valueContent;
            } else {
                return value + "+" +valueContent;
            }
        }
    }
}
