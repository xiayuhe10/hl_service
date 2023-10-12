package com.ruoyi.project.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.system.domain.SysMenuMUser;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.domain.vo.SysMenuMUserVO;
import com.ruoyi.project.system.domain.vo.SysUserMenuVO;
import com.ruoyi.project.system.mapper.SysMenuMMapper;
import com.ruoyi.project.system.mapper.SysMenuMUserMapper;
import com.ruoyi.project.system.mapper.SysUserMapper;
import com.ruoyi.project.system.service.ISysMenuMUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 应用管理Service业务层处理
 * 
 * @author ruanxiaoming
 * @date 2022-11-15
 */
@Service
public class SysMenuMUserServiceImpl implements ISysMenuMUserService
{
    @Autowired
    private SysMenuMUserMapper sysMenuMUserMapper;



    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询应用管理
     * 
     * @param id 应用管理主键
     * @return 应用管理
     */
    @Override
    public SysMenuMUser selectSysMenuMUserById(Long id)
    {
        return sysMenuMUserMapper.selectSysMenuMUserById(id);
    }

    /**
     * 查询应用管理列表
     * 
     * @param sysMenuMUser 应用管理
     * @return 应用管理
     */
    @Override
    public List<SysMenuMUser> selectSysMenuMUserList(SysMenuMUser sysMenuMUser)
    {
        return sysMenuMUserMapper.selectSysMenuMUserList(sysMenuMUser);
    }

    /**
     * 新增应用管理
     * 
     * @param sysMenuMUser 应用管理
     * @return 结果
     */
    @Override
    public int insertSysMenuMUser(SysMenuMUser sysMenuMUser)
    {
        sysMenuMUser.setCreateTime(DateUtils.getNowDate());
        return sysMenuMUserMapper.insertSysMenuMUser(sysMenuMUser);
    }

    /**
     * 修改应用管理
     * 
     * @param sysMenuMUser 应用管理
     * @return 结果
     */
    @Override
    public int updateSysMenuMUser(SysMenuMUser sysMenuMUser)
    {
        return sysMenuMUserMapper.updateSysMenuMUser(sysMenuMUser);
    }

    /**
     * 批量删除应用管理
     * 
     * @param ids 需要删除的应用管理主键
     * @return 结果
     */
    @Override
    public int deleteSysMenuMUserByIds(Long[] ids)
    {
        return sysMenuMUserMapper.deleteSysMenuMUserByIds(ids);
    }

    /**
     * 删除应用管理信息
     * 
     * @param id 应用管理主键
     * @return 结果
     */
    @Override
    public int deleteSysMenuMUserById(Long id)
    {
        return sysMenuMUserMapper.deleteSysMenuMUserById(id);
    }

    @Override
    @Transactional
    public int saveMyMenuList(List<SysMenuMUserVO> myMenuList) {
        Long userId = SecurityUtils.getUserId();
        Date nowDate = DateUtils.getNowDate();
        sysMenuMUserMapper.deleteSysMenuMUserByUserId(userId);
        for(int i = 0;i < myMenuList.size();i++){
            SysMenuMUserVO menu = myMenuList.get(i);
            SysMenuMUser sysMenuMUser = new SysMenuMUser();
            sysMenuMUser.setMenuId(menu.getMenuId());
            sysMenuMUser.setCreateId(userId);
            sysMenuMUser.setOrderNum(i+1);
            sysMenuMUser.setCreateTime(nowDate);
            sysMenuMUserMapper.insertSysMenuMUser(sysMenuMUser);
        }
        return 1;
    }

    @Override
    @Transactional
    public int initMyMenuList(SysUser sysUser) {
        Long[] roleIds = sysUser.getRoleIds();
        Long[] menuIds = new Long[9];
        for(Long roleId : roleIds){
            if(roleId == 100){
                //家长菜单id列表(班主任,我的孩子,课表,班级通知,班级相册,班级文件,作业,点评,考勤)
                menuIds = new Long[]{38L, 45L, 37L, 40L, 67L, 59L, 36L, 43L, 44L};
                break;
            }else if(roleId == 101){
                //班主任菜单id列表(班级,学生档案,课表,班级通知,班级相册,班级文件,作业,点评,考勤)
                menuIds = new Long[]{6L, 7L, 13L, 31L, 65L, 84L, 9L, 11L, 26L};
                break;
            }else if(roleId == 102){
                //老师(班级,学生档案,课表,班级通知,班级相册,班级文件,作业,点评,考勤)
                menuIds = new Long[]{6L, 7L, 13L, 31L, 65L, 84L, 9L, 11L, 26L};
                break;
            }else{
                //其他角色菜单id列表(班级,学生档案,课表,班级通知,班级相册,班级文件,作业,点评,考勤)
                menuIds = new Long[]{6L, 7L, 13L, 31L, 65L, 84L, 9L, 11L, 26L};
                break;
            }
        }
        Date nowDate = DateUtils.getNowDate();
        for(int i = 0;i < menuIds.length;i++){
            Long menuId = menuIds[i];
            SysMenuMUser sysMenuMUser = new SysMenuMUser();
            sysMenuMUser.setMenuId(menuId);
            sysMenuMUser.setCreateId(sysUser.getUserId());
            sysMenuMUser.setOrderNum(i+1);
            sysMenuMUser.setCreateTime(nowDate);
            sysMenuMUserMapper.insertSysMenuMUser(sysMenuMUser);
        }
        return 1;
    }

    @Override
    @Transactional
    public int initMyMenuListByAllUser() {
        List<SysUserMenuVO> sysUserList = sysUserMapper.selectUserListToUpdateMenu();
        for(SysUserMenuVO user : sysUserList){
            Long userId = user.getUserId();
            Long menuUserId = user.getMenuUserId();
            if(menuUserId == null){
                    Long[] roleIds = {102L};
                    SysUser sysUser = new SysUser();
                    sysUser.setUserId(userId);
                    sysUser.setRoleIds(roleIds);
                    initMyMenuList(sysUser);

            }
        }
        return 1;
    }
}
