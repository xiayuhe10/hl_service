package com.ruoyi.project.system.mapper;

import java.util.List;

import com.ruoyi.project.system.domain.CheckMsg;

/**
 * 审核信息Mapper接口
 *
 * @author shen
 * @date 2023-09-13
 */
public interface CheckMsgMapper {
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
     * 新增审核信息
     *
     * @param checkMsg 审核信息
     * @return 结果
     */
    int insertCheckMsg(CheckMsg checkMsg);

    /**
     * 查找当前用户未读消息
     * @param userId
     * @return
     */
    int selectUnReadTotalMsg(Long userId);

    /**
     * 修改审核信息
     *
     * @param checkMsg 审核信息
     * @return 结果
     */
    int updateCheckMsg(CheckMsg checkMsg);

    /**
     * 删除审核信息
     *
     * @param id 审核信息主键
     * @return 结果
     */
    int deleteCheckMsgById(Long id);

    /**
     *根据第三方id批量删除
     * @param bizId
     * @return
     */
    int deleteCheckMsgByBizId(Long bizId);
    /**
     * 批量删除审核信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteCheckMsgByIds(Long[] ids);
}
