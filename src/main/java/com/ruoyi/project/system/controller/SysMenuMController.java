package com.ruoyi.project.system.controller;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.SysMenuM;
import com.ruoyi.project.system.domain.vo.SysMenuMUserVO;
import com.ruoyi.project.system.service.ISysMenuMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 移动APP端菜单Controller
 * 
 * @author hulin
 * @date 2022-05-10
 */
@RestController
@RequestMapping("/system/SysMenuM")
public class SysMenuMController extends BaseController
{
    @Autowired
    private ISysMenuMService sysMenuMService;

    /**
     * 查询移动APP端菜单列表
     */
    @GetMapping("/list")
    public AjaxResult list(SysMenuM sysMenuM) {
        Long userId = SecurityUtils.getUserId();
        List<SysMenuM> list = sysMenuMService.selectMenuList(sysMenuM,userId);
        return AjaxResult.success(list);
    }


    /**
     * 获取移动APP端菜单详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysMenuMService.selectSysMenuMById(id));
    }
    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysMenuM sysMenuM) {
        Long userId = SecurityUtils.getUserId();
        List<SysMenuM> sysMenuMs = sysMenuMService.selectMenuList(sysMenuM, userId);
        return AjaxResult.success(sysMenuMService.buildMenuTreeSelect(sysMenuMs));
    }
    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public AjaxResult roleMenuTreeselect(@PathVariable("roleId") Long roleId)
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenuM> menus = sysMenuMService.selectMenuListByUserId(userId);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", sysMenuMService.selectMenuListByRoleId(roleId));
        ajax.put("menus", sysMenuMService.buildMenuTreeSelect(menus));
        return ajax;
    }
    /**
     * 新增移动APP端菜单
     */

    @Log(title = "移动APP端菜单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysMenuM sysMenuM) {
        if (UserConstants.NOT_UNIQUE_1.equals(sysMenuMService.checkMenuNameUnique(sysMenuM))) {
            return AjaxResult.error("新增菜单'" + sysMenuM.getName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(sysMenuM.getIsFrame()) && !StringUtils.ishttp(sysMenuM.getPath())) {
            return AjaxResult.error("新增菜单'" + sysMenuM.getName() + "'失败，地址必须以http(s)://开头");
        }
        sysMenuM.setCreateBy(SecurityUtils.getUsername());
        return toAjax(sysMenuMService.insertSysMenuM(sysMenuM));
    }

    /**
     * 修改移动APP端菜单
     */
    @Log(title = "移动APP端菜单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysMenuM sysMenuM) {
        if (UserConstants.NOT_UNIQUE_1.equals(sysMenuMService.checkMenuNameUnique(sysMenuM))) {
            return AjaxResult.error("修改菜单'" + sysMenuM.getName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(sysMenuM.getIsFrame()) && !StringUtils.ishttp(sysMenuM.getPath())) {
            return AjaxResult.error("修改菜单'" + sysMenuM.getName() + "'失败，地址必须以http(s)://开头");
        } else if (sysMenuM.getId().equals(sysMenuM.getParentId())) {
            return AjaxResult.error("修改菜单'" + sysMenuM.getName() + "'失败，上级菜单不能选择自己");
        }
        sysMenuM.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(sysMenuMService.updateSysMenuM(sysMenuM));
    }

    /**
     * 删除菜单
     */
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable("id") Long id) {
        if (sysMenuMService.hasChildByMenuId(id)) {
            return AjaxResult.error("存在子菜单,不允许删除");
        }
        if (sysMenuMService.checkMenuExistRole(id)) {
            return AjaxResult.error("菜单已分配,不允许删除");
        }
        return toAjax(sysMenuMService.deleteSysMenuMById(id));
    }

    /**
     * 获取路由信息
     * @return
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenuM> menus = sysMenuMService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(sysMenuMService.buildMenus(menus));
    }

    /**
     * 搜索获取菜单
     * @return
     */
    @GetMapping("searchMenu")
    public AjaxResult searchMenu(String searchText) {
        List<SysMenuM> sysMenuMList = sysMenuMService.searchMenu(searchText);
        return AjaxResult.success(sysMenuMList);
    }

    /**
     * 获取我的应用列表
     * @return
     */
    @GetMapping("getMyMenu")
    public AjaxResult getMyMenu() {
        List<SysMenuMUserVO> menus = sysMenuMService.selectMyMenu();
        return AjaxResult.success(menus);
    }
}
