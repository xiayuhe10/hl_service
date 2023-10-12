package com.ruoyi.project.system.service.impl;

import java.util.List;

import com.ruoyi.common.enums.CheckStatus;
import com.ruoyi.common.enums.OrderStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.ISysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.mapper.CheckMsgMapper;
import com.ruoyi.project.system.domain.CheckMsg;
import com.ruoyi.project.system.service.ICheckMsgService;

/**
 * 审核信息Service业务层处理
 *
 * @author shen
 * @date 2023-09-13
 */
@Service
public class CheckMsgServiceImpl implements ICheckMsgService {
    @Autowired
    private CheckMsgMapper checkMsgMapper;
    @Autowired
    private ISysUserService iSysUserService;

    /**
     * 查询审核信息
     *
     * @param id 审核信息主键
     * @return 审核信息
     */
    @Override
    public CheckMsg selectCheckMsgById(Long id) {
        return checkMsgMapper.selectCheckMsgById(id);
    }

    /**
     * 查询审核信息列表
     *
     * @param checkMsg 审核信息
     * @return 审核信息
     */
    @Override
    public List<CheckMsg> selectCheckMsgList(CheckMsg checkMsg) {
        return checkMsgMapper.selectCheckMsgList(checkMsg);
    }

    @Override
    public List<CheckMsg> selectMyCheckMsgList(CheckMsg checkMsg) {
        checkMsg.setUserId(SecurityUtils.getUserId());
        return checkMsgMapper.selectCheckMsgList(checkMsg);
    }

    /**
     * 根据bizId查找所有列表
     * @param bizId
     * @return
     */
    @Override
    public List<CheckMsg> selectCheckMsgByBizId(Long bizId) {
        return checkMsgMapper.selectCheckMsgByBizId(bizId);
    }

    /**
     * 根据bizId查找未操作的列表
     * @param bizId
     * @return
     */
    @Override
    public List<CheckMsg> selectCheckMsgByBizIdStatus(Long bizId) {
        return checkMsgMapper.selectCheckMsgByBizIdStatus(bizId);
    }

    /**
     * 审批
     * @param checkMsg
     * @return
     */
    @Override
    public boolean check(CheckMsg checkMsg) {
        //1判断类型
        switch(checkMsg.getType()) {
            case ORDER:  // 任务单相关
               // checkOrder(checkMsg);
                break;
            case REGISTER:  // 用户注册相关
                checkUserRegister(checkMsg);
                break;
            default:
                // 其他
        }

        return false;
    }
//    private boolean checkOrder(CheckMsg checkMsg) {
//        //1、修改当前审批状态
//        updateCheckMsg(checkMsg);
//        WorkOrder workOrder=  iWorkOrderService.selectWorkOrderById(checkMsg.getBizId());
//        //如果已拒绝，则直接拒绝
//        if(CheckStatus.REFUSE.equals(checkMsg.getStatus())){
//            workOrder.setStatus(OrderStatus.REFUSE);
//            iWorkOrderService.updateWorkOrder(workOrder);
//            //删除那些未审批人的审批信息
//            List<CheckMsg> checkMsgList=  this.selectCheckMsgByBizIdStatus(checkMsg.getBizId());
//            if(CollectionUtils.isNotEmpty(checkMsgList)){
//                for(CheckMsg msg:checkMsgList){
//                    msg.setDelFlag("1");
//                    this.updateCheckMsg(msg);
//                }
//            }
//        }else{
//            if(OrderStatus.WAITING.equals(workOrder.getStatus())){
//                workOrder.setStatus(OrderStatus.REVIEW);
//                iWorkOrderService.updateWorkOrder(workOrder);
//            }
//            //如果已通过，则进入下一步
//            //查找是否还有下一个审批人，有则修改下一个审批人的状态为审批中，无则
//            List<CheckMsg> checkMsgList=  this.selectCheckMsgByBizIdStatus(checkMsg.getBizId());
//            if(CollectionUtils.isNotEmpty(checkMsgList)){
//                //如果还存在下一个审批人 ，则直接修改列表中第一个审批人状态为审批中
//                CheckMsg msg=checkMsgList.get(0);
//                msg.setStatus(CheckStatus.REVIEW);
//                this.updateCheckMsg(msg);
//            }else{
//                //如果不存在下一个审批人，则订单直接通过
//                workOrder.setStatus(OrderStatus.PASS);
//                iWorkOrderService.updateWorkOrder(workOrder);
//            }
//        }
//        //可以发送一条消息、短信给申请人
//        return true;
//    }
    //用户注册审批
    private boolean checkUserRegister(CheckMsg checkMsg){
        //1通过还是拒绝
        //修改审核状态
        updateCheckMsg(checkMsg);
        SysUser user=new SysUser();
        user.setUserId(checkMsg.getBizId());
        if(CheckStatus.PASS.equals(checkMsg.getStatus())){
            //修改用户状态为正常：0
            user.setStatus("0");
        }else{
            //修改用户状态为作废：3
            user.setStatus("3");
            user.setDelFlag("1");
            //这里应该发送短信给对应申请用户
        }
        return iSysUserService.updateUserStatus(user)>0;
    }

    /**
     * 查找当前用户未读消息
     * @return
     */
    @Override
    public int selectUnReadTotalMsg() {

        return checkMsgMapper.selectUnReadTotalMsg(SecurityUtils.getUserId());
    }

    /**
     * 新增审核信息
     *
     * @param checkMsg 审核信息
     * @return 结果
     */
    @Override
    public int insertCheckMsg(CheckMsg checkMsg) {
        checkMsg.setCreateId(SecurityUtils.getUserId());
        checkMsg.setCreateTime(DateUtils.getNowDate());
        return checkMsgMapper.insertCheckMsg(checkMsg);
    }

    /**
     * 修改审核信息
     *
     * @param checkMsg 审核信息
     * @return 结果
     */
    @Override
    public int updateCheckMsg(CheckMsg checkMsg) {
        checkMsg.setUpdateId(SecurityUtils.getUserId());
        checkMsg.setUpdateTime(DateUtils.getNowDate());
        return checkMsgMapper.updateCheckMsg(checkMsg);
    }

    /**
     * 批量删除审核信息
     *
     * @param ids 需要删除的审核信息主键
     * @return 结果
     */
    @Override
    public int deleteCheckMsgByIds(Long[] ids) {
        return checkMsgMapper.deleteCheckMsgByIds(ids);
    }

    /**
     * 删除审核信息信息
     *
     * @param id 审核信息主键
     * @return 结果
     */
    @Override
    public int deleteCheckMsgById(Long id) {
        return checkMsgMapper.deleteCheckMsgById(id);
    }
}
