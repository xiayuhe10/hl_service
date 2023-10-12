package com.ruoyi.project.system.service;

import com.aliyuncs.exceptions.ClientException;
import com.ruoyi.project.system.domain.param.VerifyCodeParam;

import java.util.Map;

/**
 * 短信发送服务
 */
public interface SmsService {
    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    public Map<String,Object> autoCode(String phone) throws ClientException;

    /**
     * 校验验证码
     * @param verifyCodeParam
     * @return
     */
    public Map<String,Object> verifyCode(VerifyCodeParam verifyCodeParam);
}
