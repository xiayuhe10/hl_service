package com.ruoyi.common.enums;

import com.ruoyi.framework.config.IntegerValueEnum;

public enum CheckMsgType  implements IntegerValueEnum {
    ORDER(0, "任务单", "订单审核"),
    REGISTER(1, "用户注册", "用户注册审核");
    private Integer value;

    private String name;

    private String desc;

    CheckMsgType(Integer value, String name, String desc) {
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
