package com.ruoyi.project.system.domain.vo;

import com.ruoyi.common.enums.WordInputType;
import lombok.Data;

@Data
public class SysMenuMUserVO {
    private Long menuId;

    private String name;

    private String color;

    private String path;

    /** 图标类型 */
    private WordInputType imgType;

    /** 图标地址 */
    private String image;

    private String component;

    private String icon;

    private Long menuUserId;

    private Integer orderNum;
    private String perms;

    private String  query;

    private Long isFrame;
}
