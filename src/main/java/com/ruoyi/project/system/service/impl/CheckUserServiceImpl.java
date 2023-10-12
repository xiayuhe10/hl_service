package com.ruoyi.project.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.mapper.CheckUserMapper;
import com.ruoyi.project.system.domain.CheckUser;
import com.ruoyi.project.system.service.ICheckUserService;

/**
 * 审核配置人员Service业务层处理
 * 
 * @author iot
 * @date 2023-09-18
 */
@Service
public class CheckUserServiceImpl implements ICheckUserService 
{
    @Autowired
    private CheckUserMapper checkUserMapper;

    /**
     * 查询审核配置人员
     * 
     * @param id 审核配置人员主键
     * @return 审核配置人员
     */
    @Override
    public CheckUser selectCheckUserById(Long id)
    {
        return checkUserMapper.selectCheckUserById(id);
    }

    /**
     * 查询审核配置人员列表
     * 
     * @param checkUser 审核配置人员
     * @return 审核配置人员
     */
    @Override
    public List<CheckUser> selectCheckUserList(CheckUser checkUser)
    {
        return checkUserMapper.selectCheckUserList(checkUser);
    }

    /**
     * 新增审核配置人员
     * 
     * @param checkUser 审核配置人员
     * @return 结果
     */
    @Override
    public int insertCheckUser(CheckUser checkUser)
    {
        checkUser.setCreateId(SecurityUtils.getUserId());
        checkUser.setCreateTime(DateUtils.getNowDate());
        return checkUserMapper.insertCheckUser(checkUser);
    }

    /**
     * 修改审核配置人员
     * 
     * @param checkUser 审核配置人员
     * @return 结果
     */
    @Override
    public int updateCheckUser(CheckUser checkUser)
    {
        checkUser.setUpdateTime(DateUtils.getNowDate());
        return checkUserMapper.updateCheckUser(checkUser);
    }

    /**
     * 批量删除审核配置人员
     * 
     * @param ids 需要删除的审核配置人员主键
     * @return 结果
     */
    @Override
    public int deleteCheckUserByIds(Long[] ids)
    {
        return checkUserMapper.deleteCheckUserByIds(ids);
    }

    /**
     * 删除审核配置人员信息
     * 
     * @param id 审核配置人员主键
     * @return 结果
     */
    @Override
    public int deleteCheckUserById(Long id)
    {
        return checkUserMapper.deleteCheckUserById(id);
    }
}
