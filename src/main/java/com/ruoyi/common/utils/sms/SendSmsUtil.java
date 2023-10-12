package com.ruoyi.common.utils.sms;

import com.alibaba.fastjson2.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ruoyi.common.constant.SmsConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class SendSmsUtil {

    //phone:要发送的手机号
    //templateCode:申请的短信模板
    //paramsJson:模板里面的参数和对应的值，处理成json字符串直接传进来
    public static Boolean sendSms(String phone, String templateCode, Map<String,String> msg) throws ClientException {
        // 超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", SmsConstants.accessKeyId, SmsConstants.accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", SmsConstants.product, SmsConstants.domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:待发送手机号
        request.setPhoneNumbers(phone);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(SmsConstants.signName);
        // 必填:短信模板code-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        // 可选:模板中的变量替换JSON串
        request.setTemplateParam(JSONObject.toJSONString(msg));

        //可能会抛出异常,try catch一下
        SendSmsResponse sendSmsResponse;
        try {
            //调用阿里云短信服务
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("调用阿里云短信服务异常！异常信息：" + e.getMessage());
            return false;
        }

        Boolean result;
        if (result = sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            log.info("短信发送成功！内容：" + JSONObject.toJSONString(msg));
        } else {
            log.error("短信发送失败！异常信息：" + sendSmsResponse.getMessage());
        }
        return result;
    }
}
