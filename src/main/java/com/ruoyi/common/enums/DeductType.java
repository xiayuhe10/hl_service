package com.ruoyi.common.enums;

import com.ruoyi.framework.config.IntegerValueEnum;

public enum DeductType implements IntegerValueEnum {
    WEIGHT(0, "按重量", "按重量"),
    PERCENTAGE(1, "按百分比", "按百分比");
    private Integer value;

    private String name;

    private String desc;

    DeductType(Integer value, String name, String desc) {
        this.value = value;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
