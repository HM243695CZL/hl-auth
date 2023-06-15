package com.hl.admin.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hl.model.dto.AdminPageDto;
import com.hl.model.dto.AllocationRoleDto;
import com.hl.model.ums.UmsAdmin;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
public interface UmsAdminService extends IService<UmsAdmin> {

    Boolean create(UmsAdmin umsAdmin);

    Page<UmsAdmin> pageList(AdminPageDto pageDTO);

    Boolean updateAdmin(UmsAdmin umsAdmin);

    UmsAdmin view(String id);

    Boolean delete(String id);

    Boolean allocationRole(AllocationRoleDto allocationRoleDto);
}
