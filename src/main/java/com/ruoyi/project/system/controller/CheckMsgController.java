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
import com.ruoyi.project.system.domain.CheckMsg;
import com.ruoyi.project.system.service.ICheckMsgService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 审核信息Controller
 * 
 * @author shen
 * @date 2023-09-13
 */
@RestController
@RequestMapping("/system/checkMsg")
public class CheckMsgController extends BaseController
{
    @Autowired
    private ICheckMsgService checkMsgService;

    /**
     * 查询审核信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(CheckMsg checkMsg)
    {
        startPage();
        List<CheckMsg> list = checkMsgService.selectCheckMsgList(checkMsg);
        return getDataTable(list);
    }

    /**
     * 根据bizId查找所有列表
     * @param bizId
     * @return
     */
    @GetMapping("/selectCheckMsgByBizId/{bizId}")
    public TableDataInfo selectCheckMsgByBizId(@PathVariable("bizId") Long bizId) {
        startPage();
        List<CheckMsg> list = checkMsgService.selectCheckMsgByBizId(bizId);
        return getDataTable(list);
    }
    /**
     * 查询我的审核信息列表
     */
    @GetMapping("/myList")
    public TableDataInfo myList(CheckMsg checkMsg)
    {
        startPage();
        List<CheckMsg> list = checkMsgService.selectMyCheckMsgList(checkMsg);
        return getDataTable(list);
    }

    /**
     * 导出审核信息列表
     */
    @Log(title = "审核信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CheckMsg checkMsg)
    {
        List<CheckMsg> list = checkMsgService.selectCheckMsgList(checkMsg);
        ExcelUtil<CheckMsg> util = new ExcelUtil<CheckMsg>(CheckMsg.class);
        util.exportExcel(response, list, "审核信息数据");
    }

    /**
     * 获取审核信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(checkMsgService.selectCheckMsgById(id));
    }

    /**
     * 查找当前用户未读消息
     * @return
     */
    @GetMapping(value = "/selectUnReadTotalMsg")
    public AjaxResult selectUnReadTotalMsg() {
        return success(checkMsgService.selectUnReadTotalMsg());
    }
    @PostMapping("/check")
    public AjaxResult check(@RequestBody CheckMsg checkMsg)
    {
        return toAjax(checkMsgService.check(checkMsg));
    }

    /**
     * 新增审核信息
     */
    @Log(title = "审核信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CheckMsg checkMsg)
    {
        return toAjax(checkMsgService.insertCheckMsg(checkMsg));
    }

    /**
     * 修改审核信息
     */
    @Log(title = "审核信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CheckMsg checkMsg)
    {
        return toAjax(checkMsgService.updateCheckMsg(checkMsg));
    }

    /**
     * 删除审核信息
     */
    @PreAuthorize("@ss.hasPermi('system:checkMsg:remove')")
    @Log(title = "审核信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(checkMsgService.deleteCheckMsgByIds(ids));
    }
}
