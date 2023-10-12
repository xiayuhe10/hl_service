package com.ruoyi.project.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.security.RegisterBody;
import com.ruoyi.framework.security.service.SysRegisterService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.service.ISysConfigService;

/**
 * 注册验证
 * 
 * @author ruoyi
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    /**
     * 注册：这里的注册是用户注册默认冻结，需要审核通过方可登录
     * @param user
     * @return
     */
    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }

    @PostMapping("/resetPassword")
    public AjaxResult resetPassword(@RequestBody RegisterBody registerBody){

        String msg = registerService.resetPassword(registerBody);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }

}
