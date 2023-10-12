package com.ruoyi.common.enums;

import com.ruoyi.framework.config.IntegerValueEnum;

/**
 * 图标类型
 */
public enum WordInputType implements IntegerValueEnum {
    SYSTEM(0, "系统录入","系统录入"),
    USER_DEFINED(1, "用户自定义","用户自定义");
    private Integer value;

    private String name;

    private String desc;

    WordInputType(Integer value, String name, String desc)
    {
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
