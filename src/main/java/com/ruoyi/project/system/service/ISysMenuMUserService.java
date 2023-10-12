package com.ruoyi.project.system.service;

import com.ruoyi.project.system.domain.SysMenuMUser;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.domain.vo.SysMenuMUserVO;

import java.util.List;

/**
 * 应用管理Service接口
 * 
 * @author ruanxiaoming
 * @date 2022-11-15
 */
public interface ISysMenuMUserService 
{
    /**
     * 查询应用管理
     * 
     * @param id 应用管理主键
     * @return 应用管理
     */
    public SysMenuMUser selectSysMenuMUserById(Long id);

    /**
     * 查询应用管理列表
     * 
     * @param sysMenuMUser 应用管理
     * @return 应用管理集合
     */
    public List<SysMenuMUser> selectSysMenuMUserList(SysMenuMUser sysMenuMUser);

    /**
     * 新增应用管理
     * 
     * @param sysMenuMUser 应用管理
     * @return 结果
     */
    public int insertSysMenuMUser(SysMenuMUser sysMenuMUser);

    /**
     * 修改应用管理
     * 
     * @param sysMenuMUser 应用管理
     * @return 结果
     */
    public int updateSysMenuMUser(SysMenuMUser sysMenuMUser);

    /**
     * 批量删除应用管理
     * 
     * @param ids 需要删除的应用管理主键集合
     * @return 结果
     */
    public int deleteSysMenuMUserByIds(Long[] ids);

    /**
     * 删除应用管理信息
     * 
     * @param id 应用管理主键
     * @return 结果
     */
    public int deleteSysMenuMUserById(Long id);

    /**
     * 保存我的应用菜单
     * @param myMenuList
     * @return
     */
    public int saveMyMenuList(List<SysMenuMUserVO> myMenuList);

    /**
     * 初始化我的应用菜单
     * @param sysUser type(1 小程序 2 APP)
     * @return
     */
    public int initMyMenuList(SysUser sysUser);

    /**
     * 初始化所有移动端用户的应用菜单
     * @return
     */
    public int initMyMenuListByAllUser();
}
