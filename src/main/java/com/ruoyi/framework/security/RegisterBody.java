package com.ruoyi.framework.security;

import lombok.Data;

/**
 * 用户注册对象
 * 
 * @author ruoyi
 */
@Data
public class RegisterBody extends LoginBody {
    private String phone;
    private String companyCode;

}
