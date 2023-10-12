package com.ruoyi.common.enums;

import com.ruoyi.framework.config.IntegerValueEnum;

public enum CheckStatus  implements IntegerValueEnum {
    WAITING(0, "待审核", "待审核的消息，用户不可见"),
    REFUSE(1, "拒绝", "拒绝"),
    PASS(2, "审核通过", "审核通过"),
    REVIEW(3, "审核中", "审核中"),
    CANCEL(4, "已取消", "已取消"),
    LOSE(5, "已失效", "已失效");
    private Integer value;

    private String name;

    private String desc;

    CheckStatus(Integer value, String name, String desc) {
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
