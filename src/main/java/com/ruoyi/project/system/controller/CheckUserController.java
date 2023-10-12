package com.ruoyi.project.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.system.domain.CheckUser;
import com.ruoyi.project.system.service.ICheckUserService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 审核配置人员Controller
 * 
 * @author iot
 * @date 2023-09-18
 */
@RestController
@RequestMapping("/system/check/user")
public class CheckUserController extends BaseController
{
    @Autowired
    private ICheckUserService checkUserService;

    /**
     * 查询审核配置人员列表
     */
    @GetMapping("/list")
    public TableDataInfo list(CheckUser checkUser)
    {
        startPage();
        List<CheckUser> list = checkUserService.selectCheckUserList(checkUser);
        return getDataTable(list);
    }

    /**
     * 导出审核配置人员列表
     */
    @Log(title = "审核配置人员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CheckUser checkUser)
    {
        List<CheckUser> list = checkUserService.selectCheckUserList(checkUser);
        ExcelUtil<CheckUser> util = new ExcelUtil<CheckUser>(CheckUser.class);
        util.exportExcel(response, list, "审核配置人员数据");
    }

    /**
     * 获取审核配置人员详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(checkUserService.selectCheckUserById(id));
    }

    /**
     * 新增审核配置人员
     */
    @Log(title = "审核配置人员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CheckUser checkUser)
    {
        return toAjax(checkUserService.insertCheckUser(checkUser));
    }

    /**
     * 修改审核配置人员
     */
    @Log(title = "审核配置人员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CheckUser checkUser)
    {
        return toAjax(checkUserService.updateCheckUser(checkUser));
    }
    /**
     * 删除审核配置人员
     */
    @Log(title = "审核配置人员", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(checkUserService.deleteCheckUserById(id));
    }

}
