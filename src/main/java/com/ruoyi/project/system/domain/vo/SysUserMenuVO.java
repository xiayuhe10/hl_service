package com.ruoyi.project.system.domain.vo;

import lombok.Data;

/**
 * 用户关联应用菜单返回实体
 */
@Data
public class SysUserMenuVO {
    private Long userId;

    private Long deptId;

    private Long menuUserId;
}
