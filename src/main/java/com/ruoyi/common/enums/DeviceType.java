package com.ruoyi.common.enums;

import com.ruoyi.framework.config.IntegerValueEnum;

public enum DeviceType implements IntegerValueEnum {
    WEIGH_BRIDGE(0, "地磅", "地磅类型数据"),
    QQ(1, "搅拌机", "搅拌机数据"),
    ALIPAY(2,"水表","水表数据");
    private Integer value;

    private String name;

    private String desc;

    DeviceType(Integer value, String name, String desc) {
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
