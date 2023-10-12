package com.ruoyi.project.system.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.SysMenuMUser;
import com.ruoyi.project.system.domain.vo.SysMenuMUserVO;
import com.ruoyi.project.system.service.ISysMenuMUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应用管理Controller
 * 
 * @author ruanxiaoming
 * @date 2022-11-15
 */
@RestController
@RequestMapping("/system/SysMenuMUser")
public class SysMenuMUserController extends BaseController
{
    @Autowired
    private ISysMenuMUserService sysMenuMUserService;

    /**
     * 查询应用管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysMenuMUser sysMenuMUser)
    {
        startPage();
        List<SysMenuMUser> list = sysMenuMUserService.selectSysMenuMUserList(sysMenuMUser);
        return getDataTable(list);
    }

    /**
     * 获取应用管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysMenuMUserService.selectSysMenuMUserById(id));
    }

    /**
     * 新增应用管理
     */
    @Log(title = "应用管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysMenuMUser sysMenuMUser)
    {
        return toAjax(sysMenuMUserService.insertSysMenuMUser(sysMenuMUser));
    }

    /**
     * 修改应用管理
     */
    @Log(title = "应用管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysMenuMUser sysMenuMUser)
    {
        return toAjax(sysMenuMUserService.updateSysMenuMUser(sysMenuMUser));
    }

    /**
     * 删除应用管理
     */
    @Log(title = "应用管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysMenuMUserService.deleteSysMenuMUserByIds(ids));
    }

    /**
     * 保存我的应用菜单
     */
    @Log(title = "应用管理", businessType = BusinessType.INSERT)
    @PostMapping("/saveMyMenuList")
    public AjaxResult saveMyMenuList(@RequestBody List<SysMenuMUserVO> myMenuList)
    {
        return toAjax(sysMenuMUserService.saveMyMenuList(myMenuList));
    }

    /**
     * 初始化所有用户的菜单列表
     */
    @GetMapping("/initMyMenuListByAllUser")
    public AjaxResult initMyMenuListByAllUser()
    {
        return toAjax(sysMenuMUserService.initMyMenuListByAllUser());
    }


}
