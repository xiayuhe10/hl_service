package com.ruoyi.framework.config;

public interface ValueEnum <T>{
    /**
     * @return 实际存储于数据库的值
     */
    T getValue();

    /**
     * @return 简短的名称，用于下拉/单选等情况
     */
    String getName();

    /**
     * @return 详细描述，说明场景/约束等
     */
    String getDesc();
}
