package com.hl.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hl.admin.constants.Constants;
import com.hl.admin.mapper.UmsAdminMapper;
import com.hl.admin.service.UmsAdminRoleService;
import com.hl.admin.service.UmsAdminService;
import com.hl.admin.service.UmsRoleService;
import com.hl.admin.utils.JwtHelper;
import com.hl.admin.utils.MD5;
import com.hl.model.dto.AdminPageDto;
import com.hl.model.dto.AllocationRoleDto;
import com.hl.model.dto.LoginParamDto;
import com.hl.model.ums.UmsAdmin;
import com.hl.model.ums.UmsAdminRole;
import com.hl.model.ums.UmsRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {


    @Autowired
    private UmsAdminRoleService adminRoleService;

    @Autowired
    private UmsRoleService roleService;


    /**
     * 添加用户
     * @param umsAdmin
     * @return
     */
    @Transactional
    @Override
    public Boolean create(UmsAdmin umsAdmin) {
        // 设置用户初始密码
        umsAdmin.setPassword(MD5.encrypt(Constants.INIT_PASSWORD));
        boolean result = save(umsAdmin);
        List<UmsAdminRole> adminRoleList = setAdminAndRole(umsAdmin.getRoleIds(), umsAdmin.getId());
        adminRoleService.saveBatch(adminRoleList);
        return result;
    }

    @Override
    public Page<UmsAdmin> pageList(AdminPageDto pageDTO) {
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<>();
        Page<UmsAdmin> page = new Page<>(pageDTO.getPageIndex(), pageDTO.getPageSize());
        if (StrUtil.isNotEmpty(pageDTO.getUsername())) {
            queryWrapper.lambda().like(UmsAdmin::getUsername, pageDTO.getUsername());
        }
        // 根据分页查询用户
        Page<UmsAdmin> pageList = page(page, queryWrapper);
        // 根据用户id获取角色id
        page.getRecords().stream().forEach(this::setUserRoles);
        return pageList;
    }

    /**
     * 更新用户
     * @param umsAdmin
     * @return
     */
    @Override
    public Boolean updateAdmin(UmsAdmin umsAdmin) {
        UmsAdmin admin = getById(umsAdmin.getId());
        umsAdmin.setPassword(admin.getPassword());
        boolean result = updateById(umsAdmin);
        // 清空当前用户所有角色
        QueryWrapper<UmsAdminRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UmsAdminRole::getAdminId, umsAdmin.getId());
        adminRoleService.remove(queryWrapper);

        List<UmsAdminRole> adminRoleList = setAdminAndRole(umsAdmin.getRoleIds(), umsAdmin.getId());
        adminRoleService.saveBatch(adminRoleList);
        return result;
    }

    /**
     * 查看用户
     * @param id
     * @return
     */
    @Override
    public UmsAdmin view(String id) {
        UmsAdmin admin = getById(id);
        // 查询用户所有的角色id
        List<String> roleIds = adminRoleService.list(new QueryWrapper<UmsAdminRole>().eq("admin_id", id))
                .stream().map(UmsAdminRole::getRoleId).collect(Collectors.toList());
        admin.setRoleIds(roleIds);
        return admin;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Override
    public Boolean delete(String id) {
        // 清空当前用户的所有角色
        QueryWrapper<UmsAdminRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UmsAdminRole::getAdminId, id);
        adminRoleService.remove(queryWrapper);
        return removeById(id);
    }

    /**
     * 分配角色
     * @param allocationRoleDto
     * @return
     */
    @Override
    public Boolean allocationRole(AllocationRoleDto allocationRoleDto) {
        List<UmsAdminRole> adminRoleList = setAdminAndRole(allocationRoleDto.getRoleIds(), allocationRoleDto.getId());
        return adminRoleService.saveBatch(adminRoleList);
    }

    /**
     * 登录
     * @param loginParamDto
     * @param request
     * @return
     */
    @Override
    public String login(LoginParamDto loginParamDto, HttpServletRequest request) {
        String token = null;
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<>();
        String md5Password = MD5.encrypt(loginParamDto.getPassword());
        queryWrapper.lambda().eq(UmsAdmin::getUsername, loginParamDto.getUsername());
        queryWrapper.lambda().eq(UmsAdmin::getPassword, md5Password);
        UmsAdmin admin = getOne(queryWrapper);
        if (admin != null) {
            token = JwtHelper.createToken(admin.getId(), admin.getUsername());
        }
        return token;
    }

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @Override
    public UmsAdmin getUserInfoByUsername(String username) {
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UmsAdmin::getUsername, username);
        UmsAdmin admin = getOne(queryWrapper);
        // 查询用户所有的角色id
        List<String> roleIds = adminRoleService.list(new QueryWrapper<UmsAdminRole>().eq("admin_id", admin.getId()))
                .stream().map(UmsAdminRole::getRoleId).collect(Collectors.toList());
        admin.setRoleIds(roleIds);
        // 根据角色id获取角色key
        List<String> roleKeys = new ArrayList<>();
        for (String roleId : roleIds) {
            roleKeys.add(roleService.getById(roleId).getKey());
        }
        admin.setRoles(roleKeys);
        return admin;
    }

    /**
     * 设置用户和角色的关联关系
     * @param roleIds
     * @param userId
     * @return
     */
    private List<UmsAdminRole> setAdminAndRole(List<String> roleIds, String userId) {
        List<UmsAdminRole> adminRoleList = new ArrayList<>();
        for (String roleId : roleIds) {
            UmsAdminRole adminRole = new UmsAdminRole();
            adminRole.setAdminId(userId);
            adminRole.setRoleId(roleId);
            adminRoleList.add(adminRole);
        }
        return adminRoleList;
    }

    /**
     * 设置用户的roles字段
     * @param userInfo
     */
    private void setUserRoles(UmsAdmin userInfo) {
        List<String> roleIds = adminRoleService.list(new QueryWrapper<UmsAdminRole>().eq("admin_id", userInfo.getId())
                .select("role_id")).stream().map(UmsAdminRole::getRoleId).collect(Collectors.toList());
        if(!ObjectUtil.isEmpty(roleIds)) {
            // 根据角色表查询对应的角色名称
            List<String> roleName = roleService.listByIds(roleIds).stream().map(UmsRole::getKey).collect(Collectors.toList());
            userInfo.setRoles(roleName);
        }
    }
}
