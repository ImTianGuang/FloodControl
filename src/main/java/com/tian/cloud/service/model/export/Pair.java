package com.tian.cloud.service.model.export;

import com.tian.cloud.service.util.excel.annotation.DynamicField;

/**
 * @author tianguang
 * 2018/8/31 下午6:27
 **/
public class Pair<K, V> implements DynamicField <V> {

    private K key;

    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String columnName() {
        return String.valueOf(key);
    }

    @Override
    public V fieldValue() {
        return value;
    }
}
