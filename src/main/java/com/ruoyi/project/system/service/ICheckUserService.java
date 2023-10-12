package com.ruoyi.project.system.service;

import java.util.List;

import com.ruoyi.project.system.domain.CheckUser;

/**
 * 审核配置人员Service接口
 *
 * @author iot
 * @date 2023-09-18
 */
public interface ICheckUserService {
    /**
     * 查询审核配置人员
     *
     * @param id 审核配置人员主键
     * @return 审核配置人员
     */
    CheckUser selectCheckUserById(Long id);

    /**
     * 查询审核配置人员列表
     *
     * @param checkUser 审核配置人员
     * @return 审核配置人员集合
     */
    List<CheckUser> selectCheckUserList(CheckUser checkUser);

    /**
     * 新增审核配置人员
     *
     * @param checkUser 审核配置人员
     * @return 结果
     */
    int insertCheckUser(CheckUser checkUser);

    /**
     * 修改审核配置人员
     *
     * @param checkUser 审核配置人员
     * @return 结果
     */
    int updateCheckUser(CheckUser checkUser);

    /**
     * 批量删除审核配置人员
     *
     * @param ids 需要删除的审核配置人员主键集合
     * @return 结果
     */
    int deleteCheckUserByIds(Long[] ids);

    /**
     * 删除审核配置人员信息
     *
     * @param id 审核配置人员主键
     * @return 结果
     */
    int deleteCheckUserById(Long id);
}
