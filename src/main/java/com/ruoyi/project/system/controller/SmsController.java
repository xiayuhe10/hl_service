package com.ruoyi.project.system.controller;

import com.aliyuncs.exceptions.ClientException;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.project.system.domain.param.VerifyCodeParam;
import com.ruoyi.project.system.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信发送控制层
 */
@RestController
@RequestMapping("/sms")
public class SmsController extends BaseController {
    @Autowired
    private SmsService smsService;

    /**
     * 发送短信验证码
     * @param phone
     * @return
     * @throws ClientException
     */
    @PostMapping("/autoCode")
    public Object autoCode(String phone) throws ClientException {
      return smsService.autoCode(phone);
    }

    /**
     * 校验验证码
     * @param verifyCodeParam
     * @return
     * @throws ClientException
     */
    @PostMapping("/verifyCode")
    public Object verifyCode(@RequestBody VerifyCodeParam verifyCodeParam) throws ClientException {
        return smsService.verifyCode(verifyCodeParam);
    }
}
