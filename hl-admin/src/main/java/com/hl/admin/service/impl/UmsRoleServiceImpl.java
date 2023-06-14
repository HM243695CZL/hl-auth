package com.hl.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hl.admin.mapper.UmsRoleMapper;
import com.hl.admin.service.UmsRoleService;
import com.hl.model.ums.UmsRole;
import org.springframework.stereotype.Service;

@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {
}
