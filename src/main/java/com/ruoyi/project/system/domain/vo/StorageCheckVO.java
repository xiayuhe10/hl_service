package com.ruoyi.project.system.domain.vo;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.ToString;

/**
 * 入库审核对象 storage_check
 *
 * @author shen
 * @date 2023-08-15
 */
@Data
@ToString
public class StorageCheckVO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 业务id
     */
    @Excel(name = "业务id")
    private Long bizId;

    /**
     * 审核人id
     */
    @Excel(name = "审核人id")
    private Long userId;

    /**
     * 审核员名
     */
    @Excel(name = "审核员名")
    private String userName;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private String type;


    /**
     * 是否已读
     */
    @Excel(name = "是否已读")
    private String isRead;

    /**
     * 创建人id
     */
    @Excel(name = "创建人id")
    private Long createId;

    /**
     * 修改人id
     */
    @Excel(name = "修改人id")
    private Long updateId;

    private String status;

    /**
     * 仓库id
     */
    private Long storageId;
}
