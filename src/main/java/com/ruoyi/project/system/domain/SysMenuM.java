package com.ruoyi.project.system.domain;


import com.ruoyi.common.enums.WordInputType;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 移动APP端菜单对象 sys_menu_m
 *
 * @author hulin
 * @date 2022-05-10
 */
@Data
public class SysMenuM extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 菜单ID */
    private Long id;

    /** 父菜单ID */
    @Excel(name = "父菜单ID")
    private Long parentId;

    /** 菜单名称 */
    @Excel(name = "菜单名称")
    private String name;

    /** 路由地址 */
    @Excel(name = "路由地址")
    private String path;

    /** 图标类型 */
    private WordInputType imgType;

    /** 图标地址 */
    private String image;

    /** 菜单图标 */
    @Excel(name = "菜单图标")
    private String icon;

    /** 颜色 */
    @Excel(name = "颜色")
    private String color;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Long orderNum;

    /** 组件路径 */
    @Excel(name = "组件路径")
    private String component;

    /** 路由参数 */
    @Excel(name = "路由参数")
    private String query;

    /** 是否为外链（0是 1否） */
    @Excel(name = "是否为外链", readConverterExp = "0=是,1=否")
    private Long isFrame;

    /** 是否缓存（0缓存 1不缓存） */
    @Excel(name = "是否缓存", readConverterExp = "0=缓存,1=不缓存")
    private String isCache;

    /** 菜单类型（M目录 C菜单 F按钮） */
    @Excel(name = "菜单类型", readConverterExp = "M=目录,C=菜单,F=按钮")
    private String menuType;

    /** 菜单状态（0显示 1隐藏） */
    @Excel(name = "菜单状态", readConverterExp = "0=显示,1=隐藏")
    private String visible;

    /** 菜单状态（0正常 1停用） */
    @Excel(name = "菜单状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 权限标识 */
    @Excel(name = "权限标识")
    private String perms;

    /** 子菜单 */
    private List<SysMenuM> children = new ArrayList<SysMenuM>();

    /** 用户应用菜单配置表id **/
    private Long menuUserId;

    private Long userId;

}