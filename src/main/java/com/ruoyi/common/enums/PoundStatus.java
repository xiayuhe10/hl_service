package com.ruoyi.common.enums;

import com.ruoyi.framework.config.IntegerValueEnum;

public enum PoundStatus implements IntegerValueEnum {
    UN_CONNECTED(0, "未连接", "未连接"),
    UNSTABLE(1, "不稳定", "不稳定"),
    STABLE(2, "稳定", "稳定");
    private Integer value;

    private String name;

    private String desc;

    PoundStatus(Integer value, String name, String desc) {
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
