package com.ruoyi.project.system.service;

import java.util.List;

import com.ruoyi.project.system.domain.CheckMsg;

/**
 * 审核信息Service接口
 *
 * @author shen
 * @date 2023-09-13
 */
public interface ICheckMsgService {
    /**
     * 查询审核信息
     *
     * @param id 审核信息主键
     * @return 审核信息
     */
    CheckMsg selectCheckMsgById(Long id);

    /**
     * 查询审核信息列表
     *
     * @param checkMsg 审核信息
     * @return 审核信息集合
     */
    List<CheckMsg> selectCheckMsgList(CheckMsg checkMsg);

    /**
     * 查询我的审核信息列表
     *
     * @param checkMsg 审核信息
     * @return 审核信息集合
     */
    List<CheckMsg> selectMyCheckMsgList(CheckMsg checkMsg);


    /**
     * 根据bizId查找所有列表
     * @param bizId
     * @return
     */
    List<CheckMsg> selectCheckMsgByBizId(Long bizId);

    /**
     * 根据bizId查找未操作的列表
     * @param bizId
     * @return
     */
    List<CheckMsg> selectCheckMsgByBizIdStatus(Long bizId);

    /**
     * 审批
     * @param checkMsg
     * @return
     */
    boolean check(CheckMsg checkMsg);

    /**
     * 查找当前用户未读消息
     * @return
     */
    int selectUnReadTotalMsg();

    /**
     * 新增审核信息
     *
     * @param checkMsg 审核信息
     * @return 结果
     */
    int insertCheckMsg(CheckMsg checkMsg);

    /**
     * 修改审核信息
     *
     * @param checkMsg 审核信息
     * @return 结果
     */
    int updateCheckMsg(CheckMsg checkMsg);

    /**
     * 批量删除审核信息
     *
     * @param ids 需要删除的审核信息主键集合
     * @return 结果
     */
    int deleteCheckMsgByIds(Long[] ids);

    /**
     * 删除审核信息信息
     *
     * @param id 审核信息主键
     * @return 结果
     */
    int deleteCheckMsgById(Long id);
}
