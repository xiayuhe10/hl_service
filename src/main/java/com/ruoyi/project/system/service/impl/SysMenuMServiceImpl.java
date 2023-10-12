package com.ruoyi.project.system.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.system.domain.SysMenuM;
import com.ruoyi.project.system.domain.SysRole;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.domain.vo.MetaVo;
import com.ruoyi.project.system.domain.vo.RouterVo;
import com.ruoyi.project.system.domain.vo.SysMenuMUserVO;
import com.ruoyi.project.system.mapper.SysMenuMMapper;
import com.ruoyi.project.system.mapper.SysRoleMapper;
import com.ruoyi.project.system.mapper.SysRoleMenuMMapper;
import com.ruoyi.project.system.service.ISysMenuMService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 移动APP端菜单Service业务层处理
 * 
 * @author hulin
 * @date 2022-05-10
 */
@Service
public class SysMenuMServiceImpl implements ISysMenuMService
{
    @Autowired
    private SysMenuMMapper sysMenuMMapper;
    @Autowired
    private SysRoleMenuMMapper sysRoleMenuMMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 查询移动APP端菜单
     * 
     * @param id 移动APP端菜单主键
     * @return 移动APP端菜单
     */
    @Override
    public SysMenuM selectSysMenuMById(Long id)
    {
        return sysMenuMMapper.selectSysMenuMById(id);
    }

    /**
     * 查询移动APP端菜单列表
     * 
     * @param sysMenuM 移动APP端菜单
     * @return 移动APP端菜单
     */
    @Override
    public List<SysMenuM> selectSysMenuMList(SysMenuM sysMenuM)
    {
        return sysMenuMMapper.selectSysMenuMList(sysMenuM);
    }

    /**
     * PC查询系统APP菜单列表
     * @param sysMenuM
     * @param userId
     * @return
     */
    @Override
    public List<SysMenuM> selectMenuList(SysMenuM sysMenuM, Long userId) {
        List<SysMenuM> menuList = null;
        // 管理员显示所有菜单信息
        if (SysUser.isAdmin(userId)) {
            menuList = sysMenuMMapper.selectSysMenuMList(sysMenuM);
        } else {
            sysMenuM.getParams().put("userId", userId);
            menuList = sysMenuMMapper.selectSysMenuMListByUserId(sysMenuM);
        }
        return menuList;
    }

    /**
     * 校验菜单名称是否唯一
     * @param menu
     * @return
     */
    @Override
    public String checkMenuNameUnique(SysMenuM menu) {
        Long menuId = StringUtils.isNull(menu.getId()) ? -1L : menu.getId();
        SysMenuM info = sysMenuMMapper.checkMenuNameUnique(menu.getName(), menu.getParentId());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != menuId.longValue()) {
            return UserConstants.NOT_UNIQUE_1;
        }
        return UserConstants.UNIQUE_0;
    }

    /**
     * 根据角色ID查询菜单树信息
     * @param roleId
     * @return
     */
    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        SysRole role = sysRoleMapper.selectRoleById(roleId);
        return sysMenuMMapper.selectMenuListByRoleId(roleId, role.isMenuCheckStrictly());
    }

    /**
     *  根据用户Id查询移动端菜单权限列表
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms = sysMenuMMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户查询系统菜单列表
     * @param userId
     * @return
     */
    @Override
    public List<SysMenuM> selectMenuListByUserId(Long userId) {
        return selectMenuList(new SysMenuM(), userId);
    }

    /**
     * 查询菜单使用数量
     * @param menuId
     * @return
     */
    @Override
    public boolean checkMenuExistRole(Long menuId) {
        int result = sysRoleMenuMMapper.checkMenuExistRole(menuId);
        return result > 0 ? true : false;
    }

    /**
     * 是否存在菜单子节点
     * @param menuId
     * @return
     */
    @Override
    public boolean hasChildByMenuId(Long menuId) {
        int result = sysMenuMMapper.hasChildByMenuId(menuId);
        return result > 0 ? true : false;
    }

    /**
     * 构建前端所需要下拉树结构
     * @param menus
     * @return
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenuM> menus) {
        List<SysMenuM> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }
    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    @Override
    public List<SysMenuM> buildMenuTree(List<SysMenuM> menus)
    {
        List<SysMenuM> returnList = new ArrayList<SysMenuM>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysMenuM dept : menus) {
            tempList.add(dept.getId());
        }
        for (Iterator<SysMenuM> iterator = menus.iterator(); iterator.hasNext();)
        {
            SysMenuM menu = (SysMenuM) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId()))
            {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = menus;
        }
        return returnList;
    }
    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenuM> list, SysMenuM t)
    {
        // 得到子节点列表
        List<SysMenuM> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenuM tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }
    /**
     * 得到子节点列表
     */
    private List<SysMenuM> getChildList(List<SysMenuM> list, SysMenuM t)
    {
        List<SysMenuM> tlist = new ArrayList<SysMenuM>();
        Iterator<SysMenuM> it = list.iterator();
        while (it.hasNext())
        {
            SysMenuM n = (SysMenuM) it.next();
            if (n.getParentId().longValue() == t.getId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenuM> list, SysMenuM t) {
        return getChildList(list, t).size() > 0;
    }
    /**
     * 新增移动APP端菜单
     * 
     * @param sysMenuM 移动APP端菜单
     * @return 结果
     */
    @Override
    public int insertSysMenuM(SysMenuM sysMenuM)
    {
        sysMenuM.setCreateTime(DateUtils.getNowDate());
        return sysMenuMMapper.insertSysMenuM(sysMenuM);
    }

    /**
     * 修改移动APP端菜单
     * 
     * @param sysMenuM 移动APP端菜单
     * @return 结果
     */
    @Override
    public int updateSysMenuM(SysMenuM sysMenuM)
    {
        sysMenuM.setUpdateTime(DateUtils.getNowDate());
        return sysMenuMMapper.updateSysMenuM(sysMenuM);
    }

    /**
     * 批量删除移动APP端菜单
     * 
     * @param ids 需要删除的移动APP端菜单主键
     * @return 结果
     */
    @Override
    public int deleteSysMenuMByIds(Long[] ids)
    {
        return sysMenuMMapper.deleteSysMenuMByIds(ids);
    }

    /**
     * 删除移动APP端菜单信息
     * 
     * @param id 移动APP端菜单主键
     * @return 结果
     */
    @Override
    public int deleteSysMenuMById(Long id)
    {
        return sysMenuMMapper.deleteSysMenuMById(id);
    }

    /**
     * 根据用户ID查询菜单
     * @param userId
     * @return
     */
    @Override
    public List<SysMenuM> selectMenuTreeByUserId(Long userId) {
        List<SysMenuM> menus = null;
        if (SecurityUtils.isAdmin(userId)) {
            menus = sysMenuMMapper.selectMenuTreeAll();
        } else {
            menus = sysMenuMMapper.selectMenuTreeByUserId(userId);
        }
        return getChildPerms(menus, 0);
    }

    /**
     * 构建前端路由所需要的菜单
     * @param menus
     * @return
     */
    @Override
    public List<RouterVo> buildMenus(List<SysMenuM> menus) {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenuM menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setImgType(menu.getImgType());
            router.setImage(menu.getImage());
            router.setComponent(getComponent(menu));
            router.setColor(menu.getColor());
            router.setQuery(menu.getQuery());
            router.setIsFrame(menu.getIsFrame());

            MetaVo metaVo = new MetaVo(menu.getName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()), menu.getPath());
            metaVo.setMenuId(menu.getId());
            metaVo.setMenuUserId(menu.getMenuUserId());
            router.setMeta(metaVo);

            List<SysMenuM> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && cMenus.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            } else if (isMenuFrame(menu)) {
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));

                MetaVo metaVo1 = new MetaVo(menu.getName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()), menu.getPath());
                metaVo1.setMenuId(menu.getId());
                metaVo1.setMenuUserId(menu.getMenuUserId());
                children.setMeta(metaVo1);

                children.setQuery(menu.getQuery());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (menu.getParentId().intValue() == 0 && isInnerLink(menu)) {
                router.setMeta(new MetaVo(menu.getName(), menu.getIcon()));
                router.setPath("/");
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(UserConstants.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));

                MetaVo metaVo1 = new MetaVo(menu.getName(), menu.getIcon(), menu.getPath());
                metaVo1.setMenuId(menu.getId());
                metaVo1.setMenuUserId(menu.getMenuUserId());
                children.setMeta(metaVo1);

                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    @Override
    public List<SysMenuMUserVO> selectMyMenu() {
        return sysMenuMMapper.selectMenuByUserId(SecurityUtils.getUserId());
    }

    @Override
    public List<SysMenuM> searchMenu(String searchText) {
        SysMenuM sysMenuM = new SysMenuM();
        sysMenuM.setName(searchText);
        sysMenuM.setUserId(SecurityUtils.getUserId());
        List<SysMenuM> sysMenuMList = sysMenuMMapper.selectMenuByUserIdAndName(sysMenuM);
        return sysMenuMList;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenuM menu)
    {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            component = UserConstants.INNER_LINK;
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }
    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(SysMenuM menu)
    {
        return menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }
    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenuM menu)
    {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame())) {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }
    /**
     * 内链域名特殊字符替换
     *
     * @return
     */
    public String innerLinkReplaceEach(String path)
    {
        return StringUtils.replaceEach(path, new String[] { Constants.HTTP, Constants.HTTPS },
                new String[] { "", "" });
    }

    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(SysMenuM menu)
    {
        return menu.getIsFrame().equals(UserConstants.NO_FRAME) && StringUtils.ishttp(menu.getPath());
    }
    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    private String getRouteName(SysMenuM menu)
    {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu))
        {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }
    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(SysMenuM menu) {
        return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(UserConstants.NO_FRAME);
    }
    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list 分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenuM> getChildPerms(List<SysMenuM> list, int parentId)
    {
        List<SysMenuM> returnList = new ArrayList<SysMenuM>();
        for (Iterator<SysMenuM> iterator = list.iterator(); iterator.hasNext();)
        {
            SysMenuM t = (SysMenuM) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId)
            {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }
}
