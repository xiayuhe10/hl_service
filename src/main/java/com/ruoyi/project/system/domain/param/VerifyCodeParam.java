package com.ruoyi.project.system.domain.param;

import lombok.Data;

/**
 * 校验验证码传参
 */
@Data
public class VerifyCodeParam {
    //验证码
    private Integer code;
    //手机号
    private String phone;
}
