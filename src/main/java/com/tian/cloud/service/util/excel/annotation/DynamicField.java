package com.tian.cloud.service.util.excel.annotation;

public interface DynamicField<V> {

    String columnName();

    V fieldValue();
}
