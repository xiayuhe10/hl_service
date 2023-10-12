package com.ruoyi.common.enums;

import com.ruoyi.framework.config.IntegerValueEnum;

public enum ReadStatus implements IntegerValueEnum {
    UNREAD(0, "未读", "未读"),
    READ(1, "已读", "已读");
    private Integer value;

    private String name;

    private String desc;

    ReadStatus(Integer value, String name, String desc) {
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
