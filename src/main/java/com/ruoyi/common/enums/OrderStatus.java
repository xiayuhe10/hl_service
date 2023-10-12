package com.ruoyi.common.enums;

import com.ruoyi.framework.config.IntegerValueEnum;

public enum OrderStatus implements IntegerValueEnum {

    WAITING(0, "待审核", "待审核"),
    REFUSE(1, "已拒绝", "已拒绝"),
    PASS(2, "审核通过", "审核通过"),
    PRODUCING(3, "生产中", "生产中"),
    COMPLETE(4, "已完成", "已完成"),
    CANCEL(5, "已取消", "已取消"),
    PAUSE(6, "暂停中", "暂停中"),
    REVIEW(7, "审批中", "审批中");

    private Integer value;

    private String name;

    private String desc;

    OrderStatus(Integer value, String name, String desc) {
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
