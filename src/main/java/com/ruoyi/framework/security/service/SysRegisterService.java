package com.ruoyi.framework.security.service;

import com.ruoyi.common.enums.CheckMsgType;
import com.ruoyi.common.enums.CheckStatus;
import com.ruoyi.common.enums.ReadStatus;
import com.ruoyi.project.system.domain.CheckMsg;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.domain.param.VerifyCodeParam;
import com.ruoyi.project.system.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.redis.RedisCache;
import com.ruoyi.framework.security.RegisterBody;
import com.ruoyi.project.system.domain.SysUser;

import java.util.List;
import java.util.Map;

/**
 * 注册校验方法
 * 
 * @author ruoyi
 */
@Component
public class SysRegisterService
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysDeptService iSysDeptService;

    @Autowired
    private ICheckMsgService checkMsgService;

    /**
     * 注册
     */
    public String register(RegisterBody registerBody)
    {
        String msg = "", username = registerBody.getUsername(), password = registerBody.getPassword(),phone =registerBody.getPhone(),companyCode=registerBody.getCompanyCode();
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setNickName(username);
        sysUser.setPhonenumber(phone);
        // 验证码开关(图形验证码)
//        boolean captchaEnabled = configService.selectCaptchaEnabled();
//        if (captchaEnabled) {
//            validateCaptcha(phone, registerBody.getCode(), registerBody.getUuid());
//        }

        //根据手机号查找用户，如果该用户为冻结状态，则提示用户冻结，请联系管理员
        SysUser sysUserParam=  userService.selectUserByUserName(phone);
        if(StringUtils.isNotNull(sysUserParam)){
            if("2".equals(sysUserParam.getStatus())||"1".equals(sysUserParam.getStatus())){
                msg = "账号已冻结，请联系管理员";
                return msg;
            } else if("0".equals(sysUserParam.getStatus())){
                msg = "账号已存在，请勿重复操作";
                return msg;
            }
        }
        if(StringUtils.isEmpty(companyCode)){
            msg = "公司编码不能为空";
            return msg;
        }
        //查找公司，如果公司存在，则给用户赋值部门
        SysDept  sysDept=iSysDeptService.checkDeptByCode(companyCode);
        if(StringUtils.isNotNull(sysDept)){
            sysUser.setDeptId(sysDept.getDeptId());
        }else{
            msg = "公司不存在";
            return msg;
        }
        //用户相关判断
        if (StringUtils.isEmpty(username)) {
            msg = "用户名不能为空";
        }else if (StringUtils.isEmpty(password)) {
            msg = "用户密码不能为空";
        } else if (username.length() < UserConstants.USERNAME_MIN_LENGTH || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            msg = "账户长度必须在2到20个字符之间";
        }
        else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            msg = "密码长度必须在5到20个字符之间";
        }else if(StringUtils.isEmpty(companyCode)){
            msg = "公司编码不能为空";
        }else if(StringUtils.isEmpty(phone)){
            msg = "手机号不能为空";
        }else if (!userService.checkPhoneUnique(sysUser)) {
            msg = "保存用户'" + username + "'失败，"+phone+"手机号已注册";
        } else {
            sysUser.setNickName(username);
            sysUser.setPassword(SecurityUtils.encryptPassword(password));
            //设置为冻结状态
            sysUser.setStatus("2");
            int regFlag = userService.insertUser(sysUser);
            if (regFlag>0) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.register.success")));
                //查找当前公司的管理员
                List<SysUser>  userList=userService.selectManagerBydeptId(sysDept.getDeptId());
                if(CollectionUtils.isNotEmpty(userList)){
                    SysUser user=userList.get(0);
                    //发送一条审核信息给对应公司的管理员进行审核
                    CheckMsg checkMsg=new CheckMsg();
                    checkMsg.setUserId(user.getUserId());
                    checkMsg.setBizId(sysUser.getUserId());
                    checkMsg.setType(CheckMsgType.REGISTER);
                    checkMsg.setStatus(CheckStatus.REVIEW);
                    checkMsg.setIsRead(ReadStatus.UNREAD);
                    checkMsgService.insertCheckMsg(checkMsg);
                }else{
                    msg = "该公司管理员为空,请联系系统管理人员";
                }

            } else {
                msg = "注册失败,请联系系统管理人员";
            }
        }
        return msg;
    }
    public String resetPassword(RegisterBody registerBody){
        String msg = "";
        if(StringUtils.isEmpty(registerBody.getPhone())){
            msg = "手机号不能为空";
            return msg;
        }
        if(StringUtils.isEmpty(registerBody.getPassword())){
            msg = "密码不能为空";
            return msg;
        }
        //根据手机号查找用户
        SysUser user=   userService.selectUserByPhone(registerBody.getPhone());
        if(StringUtils.isNotNull(user)){
            //根据手机号码修改密码
            int regFlag=userService.updatePasswordByPhone(SecurityUtils.encryptPassword(registerBody.getPassword()),registerBody.getPhone());
            if(regFlag>0){
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(registerBody.getPhone(), Constants.REGISTER, MessageUtils.message("user.resetPassword.success")));
            }else{
                msg = "重置密码失败,请联系系统管理人员";
            }
        }else{
            msg = "未查找到该用户信息，请注册新用户";
        }

        return msg;
    }
    /**
     * 校验验证码
     * 
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            throw new CaptchaException();
        }
    }
}
