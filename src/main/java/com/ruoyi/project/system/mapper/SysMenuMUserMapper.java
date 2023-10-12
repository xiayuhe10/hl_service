package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.SysMenuMUser;


import java.util.List;

/**
 * 应用管理Mapper接口
 * 
 * @author ruanxiaoming
 * @date 2022-11-15
 */
public interface SysMenuMUserMapper 
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
     * 删除应用管理
     * 
     * @param id 应用管理主键
     * @return 结果
     */
    public int deleteSysMenuMUserById(Long id);

    /**
     * 批量删除应用管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysMenuMUserByIds(Long[] ids);

    /**
     * 根据用户id删除应用菜单
     *
     * @param userId 用户id
     * @return 结果
     */
    public int deleteSysMenuMUserByUserId(Long userId);

}
