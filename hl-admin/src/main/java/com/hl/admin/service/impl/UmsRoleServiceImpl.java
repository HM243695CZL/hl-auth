package com.hl.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hl.admin.mapper.UmsRoleMapper;
import com.hl.admin.service.UmsRoleService;
import com.hl.model.dto.RolePageDto;
import com.hl.model.ums.UmsRole;
import org.springframework.stereotype.Service;

@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {

    @Override
    public Page<UmsRole> pageList(RolePageDto roleDTO) {
        Page<UmsRole> page = new Page<>(roleDTO.getPageIndex(), roleDTO.getPageSize());
        QueryWrapper<UmsRole> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(roleDTO.getName())) {
            queryWrapper.lambda().like(UmsRole::getName, roleDTO.getName());
        }
        return page(page, queryWrapper);
    }
}
