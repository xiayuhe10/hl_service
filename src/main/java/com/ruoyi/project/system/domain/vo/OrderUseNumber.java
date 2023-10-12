package com.ruoyi.project.system.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderUseNumber {
    //混凝土方量
    private BigDecimal concreteTotal;
    //砂浆方量
    private BigDecimal mortarTotal;
}
