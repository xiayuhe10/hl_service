package com.ruoyi.project.system.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.ruoyi.common.constant.SmsConstants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sms.SendSmsUtil;
import com.ruoyi.framework.redis.RedisCache;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.domain.param.VerifyCodeParam;
import com.ruoyi.project.system.service.ISysUserService;
import com.ruoyi.project.system.service.SmsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 短信发送实现
 */
@Log4j2
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RedisCache redisService;
    @Autowired
    private ISysUserService userService;
    private static String REGEX_PHONE = "^[1]\\d{10}$";

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     * @throws ClientException
     */
    @Override
    public Map<String, Object> autoCode(String phone) throws ClientException {
        Map<String, Object> reMap = new HashMap<>();
//        SysUser user= userService.selectUserByPhone(phone);
//        if(StringUtils.isNull(user)){
//            reMap.put("bool", false);
//            reMap.put("msg", "未找到该用户，请注册新用户");
//            return reMap;
//        }
        boolean boolPhone = regexPhone(phone);
        if (!boolPhone) {
            reMap.put("bool", false);
            reMap.put("msg", "手机号码不正确！请重新输入");
            return reMap;
        }

        Integer oldNumber = redisService.getCacheObject(phone + "number");
        if (null != oldNumber && oldNumber >= 5) {
            reMap.put("bool", false);
            reMap.put("msg", "验证码发送已超过5条！请24小时后在重试！");
            return reMap;
        }
        if (null == oldNumber) {
            oldNumber = 0;
        }
        redisService.setCacheObject(phone + "number", oldNumber + 1, 24, TimeUnit.HOURS);
        Map<String, String> map = new HashMap<>();
        int code = Integer.valueOf(StringUtils.getRandom(5));
        map.put("code", String.valueOf(code));
        redisService.setCacheObject(phone + SmsConstants.code, code, 5, TimeUnit.MINUTES);
        boolean bool = SendSmsUtil.sendSms(phone, SmsConstants.AUTO_CODE, map);
        reMap.put("bool", bool);
        reMap.put("msg", bool ? "验证码发送成功！" : "验证码发送失败！");
        return reMap;
    }

    /**
     * 校验验证码
     *
     * @param verifyCodeParam
     * @return
     */
    @Override
    public Map<String, Object> verifyCode(VerifyCodeParam verifyCodeParam) {
        Map<String, Object> reMap = new HashMap<>();
//        SysUser user= userService.selectUserByPhone(verifyCodeParam.getPhone());
//        if(StringUtils.isNull(user)){
//            reMap.put("bool", false);
//            reMap.put("msg", "未找到该用户，请注册新用户");
//            return reMap;
//        }
        Integer oldCode = redisService.getCacheObject(verifyCodeParam.getPhone() + SmsConstants.code);
        if (null == oldCode) {
            reMap.put("bool", false);
            reMap.put("msg", "验证码已过期！请重新发送");
            return reMap;
        }
        if (null == verifyCodeParam.getCode()) {
            reMap.put("bool", false);
            reMap.put("msg", "验证码不能为空！");
            return reMap;
        }
        if (verifyCodeParam.getCode().equals(oldCode)) {
            reMap.put("bool", true);
            reMap.put("msg", "验证码正确！");
        } else {
            reMap.put("bool", false);
            reMap.put("msg", "验证码不正确！");
        }
        return reMap;
    }

    /**
     * 校验手机号格式是否正确
     *
     * @param phone
     * @return
     */
    private static Boolean regexPhone(String phone) {
        Boolean b;
        if (phone.length() != 11) {
            b = false;
        } else {
            Pattern p = Pattern.compile(REGEX_PHONE);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (isMatch) {
                b = true;
            } else {
                b = false;
            }
        }
        return b;
    }
}
