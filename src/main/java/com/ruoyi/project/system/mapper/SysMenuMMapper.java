package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.SysMenuM;
import com.ruoyi.project.system.domain.vo.SysMenuMUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 移动APP端菜单Mapper接口
 * 
 * @author hulin
 * @date 2022-05-10
 */
public interface SysMenuMMapper {
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
     * PC查询菜单列表
     * @param sysMenuM
     * @return
     */
    public List<SysMenuM> selectSysMenuMListByUserId(SysMenuM sysMenuM);
    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @param menuCheckStrictly 菜单树选择项是否关联显示
     * @return 选中菜单列表
     */
    public List<Long> selectMenuListByRoleId(@Param("roleId") Long roleId, @Param("menuCheckStrictly") boolean menuCheckStrictly);

    /**
     * 根据用户Id查询移动端菜单权限列表
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public List<String> selectMenuPermsByUserId(Long userId);

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    public int hasChildByMenuId(Long menuId);
    /**
     *  校验菜单名称是否唯一
     * @param name
     * @param parentId
     * @return
     */
   public SysMenuM checkMenuNameUnique(@Param("name") String name, @Param("parentId") Long parentId);
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
     * 删除移动APP端菜单
     * 
     * @param id 移动APP端菜单主键
     * @return 结果
     */
    public int deleteSysMenuMById(Long id);

    /**
     * 批量删除移动APP端菜单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysMenuMByIds(Long[] ids);

    /**
     * 如果是管理员，则获取所有菜单
     * @return
     */
    public List<SysMenuM> selectMenuTreeAll();
    /**
     * 如果不是管理员，则获取对应角色菜单
     * @return
     */
    public List<SysMenuM>  selectMenuTreeByUserId(Long userId);

    /**
     * 获取我的应用列表
     * @return
     */
    public List<SysMenuMUserVO> selectMenuByUserId(Long userId);

    /**
     * 获取要初始化的应用id列表
     * @param menuParentName
     * @param menuNameList
     * @return
     */
    public List<Long> selectMenuIdsByName(@Param("menuParentName") String menuParentName,@Param("menuNameList") String[] menuNameList);

    /**
     * 根据用户id和菜单名称关键字获取菜单
     * @param sysMenuM
     * @return
     */
    public List<SysMenuM> selectMenuByUserIdAndName(SysMenuM sysMenuM);
}
