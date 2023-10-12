package com.ruoyi.project.system.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 审核配置人员对象 check_user
 * 
 * @author iot
 * @date 2023-09-18
 */
@Data
@ToString
public class CheckUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 搅拌站项目id */
    @Excel(name = "搅拌站项目id")
    private Long plantId;

    /** 类型（0订单审核） */
    @Excel(name = "类型", readConverterExp = "0=订单审核")
    private String type;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    private String userName;

    private String avatar;

    /** 删除标识 */
    private String delFlag;

    private Long createId;

    private Long updateId;

    public CheckUser(Long plantId) {
        this.plantId = plantId;
    }

    public CheckUser() {
    }
}
