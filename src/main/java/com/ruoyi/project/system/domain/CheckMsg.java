package com.ruoyi.project.system.domain;

import com.ruoyi.common.enums.CheckMsgType;
import com.ruoyi.common.enums.CheckStatus;
import com.ruoyi.common.enums.ReadStatus;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 审核信息对象 check_msg
 * 
 * @author shen
 * @date 2023-09-13
 */
@Data
@ToString
public class CheckMsg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 业务id */
    @Excel(name = "业务id")
    private Long bizId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 用户名 */
    private String userName;

    /** 类型（0订单、1用户注册） */
    @Excel(name = "类型", readConverterExp = "0=订单、1用户注册")
    private CheckMsgType type;

    /** 审批状态（0待审核、1拒绝、2通过） */
    @Excel(name = "审批状态", readConverterExp = "0=待审核、1拒绝、2通过")
    private CheckStatus status;

    /** 审核意见 */
    @Excel(name = "审核意见")
    private String opinion;

    /** 是否已读（0未读、1已读） */
    @Excel(name = "是否已读", readConverterExp = "0=未读、1已读")
    private ReadStatus isRead;

    private Long createId;

    private Long updateId;

    /** 删除标识 */
    private String delFlag;
}
