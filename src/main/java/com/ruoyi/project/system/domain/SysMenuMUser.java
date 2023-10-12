package com.ruoyi.project.system.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 应用管理对象 sys_menu_m_user
 * 
 * @author ruanxiaoming
 * @date 2022-11-15
 */
@Data
public class SysMenuMUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 菜单id */
    @Excel(name = "菜单id")
    private Long menuId;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer orderNum;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createId;
}
