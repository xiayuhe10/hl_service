package com.ruoyi.project.system.service;

import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.system.domain.SysMenuM;
import com.ruoyi.project.system.domain.vo.RouterVo;
import com.ruoyi.project.system.domain.vo.SysMenuMUserVO;


import java.util.List;
import java.util.Set;

/**
 * 移动APP端菜单Service接口
 * 
 * @author hulin
 * @date 2022-05-10
 */
public interface ISysMenuMService 
{
    /**
     * 查询移动APP端菜单
     * 
     * @param id 移动APP端菜单主键
     * @return 移动APP端菜单
     */
    public SysMenuM selectSysMenuMById(Long id);

    /**
     * 查询移动APP端菜单列表
     * 
     * @param sysMenuM 移动APP端菜单
     * @return 移动APP端菜单集合
     */
    public List<SysMenuM> selectSysMenuMList(SysMenuM sysMenuM);

    /**
     * PC查询系统APP菜单列表
     * @param sysMenuM
     * @param userId
     * @return
     */
    public List<SysMenuM> selectMenuList(SysMenuM sysMenuM, Long userId);

    /**
     * 校验菜单名称是否唯一
     * @param menu
     * @return
     */
    public String checkMenuNameUnique(SysMenuM menu);

    /**
     * 根据角色ID查询菜单树信息
     * @param roleId
     * @return
     */
    public List<Long> selectMenuListByRoleId(Long roleId);

    /**
     *  根据用户Id查询移动端菜单权限列表
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectMenuPermsByUserId(Long userId);

    /**
     *根据用户查询系统菜单列表
     * @param userId
     * @return
     */
    public List<SysMenuM> selectMenuListByUserId(Long userId);
    /**
     * 查询菜单使用数量
     * @param menuId
     * @return
     */
    public boolean checkMenuExistRole(Long menuId);
    /**
     * 是否存在菜单子节点
     * @param menuId
     * @return
     */
    public boolean hasChildByMenuId(Long menuId);

    /**
     * 构建前端所需要树结构
     * @param menus
     * @return
     */
    public List<SysMenuM> buildMenuTree(List<SysMenuM> menus);
    /**
     * 构建前端所需要下拉树结构
     * @param menus
     * @return
     */
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenuM> menus);
    /**
     * 新增移动APP端菜单
     * 
     * @param sysMenuM 移动APP端菜单
     * @return 结果
     */
    public int insertSysMenuM(SysMenuM sysMenuM);

    /**
     * 修改移动APP端菜单
     * 
     * @param sysMenuM 移动APP端菜单
     * @return 结果
     */
    public int updateSysMenuM(SysMenuM sysMenuM);

    /**
     * 批量删除移动APP端菜单
     * 
     * @param ids 需要删除的移动APP端菜单主键集合
     * @return 结果
     */
    public int deleteSysMenuMByIds(Long[] ids);

    /**
     * 删除移动APP端菜单信息
     * 
     * @param id 移动APP端菜单主键
     * @return 结果
     */
    public int deleteSysMenuMById(Long id);

    /**
     * 根据用户ID查询菜单
     * @param userId
     * @return
     */
    public List<SysMenuM> selectMenuTreeByUserId(Long userId);

    /**
     * 构建前端路由所需要的菜单
     * @param menus
     * @return
     */
    public List<RouterVo> buildMenus(List<SysMenuM> menus);

    /**
     * 获取我的应用列表
     * @return
     */
    public List<SysMenuMUserVO> selectMyMenu();

    /**
     * 搜索应用
     * @return
     */
    public List<SysMenuM> searchMenu(String searchText);
}
