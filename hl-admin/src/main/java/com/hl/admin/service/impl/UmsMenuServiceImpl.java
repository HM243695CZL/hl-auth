package com.hl.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hl.admin.exception.ApiException;
import com.hl.admin.mapper.UmsMenuMapper;
import com.hl.admin.service.UmsAdminRoleService;
import com.hl.admin.service.UmsMenuService;
import com.hl.admin.service.UmsRoleMenuService;
import com.hl.admin.service.UmsRoleService;
import com.hl.admin.utils.MenuHelper;
import com.hl.model.dto.InitMenuDto;
import com.hl.model.dto.MenuMataDto;
import com.hl.model.ums.UmsAdminRole;
import com.hl.model.ums.UmsMenu;
import com.hl.model.ums.UmsRole;
import com.hl.model.ums.UmsRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements UmsMenuService {

    @Autowired
    private UmsRoleMenuService roleMenuService;

    @Autowired
   private UmsAdminRoleService adminRoleService;

    @Autowired
    private UmsRoleService roleService;

    @Transactional
    @Override
    public Boolean delete(String id) {
        // 如果存在子菜单，则不能删除
        QueryWrapper<UmsMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UmsMenu::getPid, id);
        List<UmsMenu> list = list(queryWrapper);
        if (list.size() > 0) {
            throw new ApiException("当前菜单存在子菜单，不能删除");
        }
        // 清空菜单对应的所有角色
        QueryWrapper<UmsRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsRoleMenu::getMenuId, id);
        roleMenuService.remove(wrapper);
        return removeById(id);
    }

    /**
     * 根据用户id获取用户菜单
     * @param id
     * @return
     */
    @Override
    public List<InitMenuDto> getMenuListByUserId(String id) {
        List<InitMenuDto> dataList = new ArrayList<>();
        List<InitMenuDto> menus = new ArrayList<>();
        // 获取用户对应的角色id
        List<String> roleIds = adminRoleService.list(new QueryWrapper<UmsAdminRole>().eq("admin_id", id).select("role_id"))
                .stream().map(UmsAdminRole::getRoleId).collect(Collectors.toList());
        // 根据角色id查询对应的菜单id
        List<String> menuIds = roleMenuService.list(new QueryWrapper<UmsRoleMenu>().in("role_id", roleIds).select("menu_id"))
                .stream().map(UmsRoleMenu::getMenuId).collect(Collectors.toList());
        // 根据菜单id获取对应的菜单父id(需要对数据进行空值判断【objects::nonNull】)
        List<String> pIds = list(new QueryWrapper<UmsMenu>().in("id", menuIds).select("pid"))
                .stream().filter(Objects::nonNull).map(UmsMenu::getPid).collect(Collectors.toList());
        // 将菜单父id添加到menuIds中，以便查出父级菜单信息
        menuIds.addAll(pIds);
        // 根据菜单id查询出菜单
        list(new QueryWrapper<UmsMenu>().in("id", menuIds).lambda().orderBy(true, true, UmsMenu::getSort))
                .stream().forEach(menuItem -> {
            // 根据角色表查询对应的角色key
            List<String> roleKey = roleService.listByIds(roleIds).stream().map(UmsRole::getKey).collect(Collectors.toList());
            InitMenuDto initMenuDTO = new InitMenuDto();
            MenuMataDto menuMataDTO = new MenuMataDto();
            // 设置meta
            menuMataDTO.setTitle(menuItem.getTitle());
            menuMataDTO.setIsLink(menuItem.getIsLink());
            menuMataDTO.setIsHide(menuItem.getIsHide());
            menuMataDTO.setIsKeepAlive(menuItem.getIsKeepAlive());
            menuMataDTO.setIsAffix(menuItem.getIsAffix());
            menuMataDTO.setIsIframe(menuItem.getIsIframe());
            menuMataDTO.setRoles(roleKey);
            menuMataDTO.setIcon(menuItem.getIcon());

            initMenuDTO.setId(menuItem.getId());
            initMenuDTO.setPid(menuItem.getPid());
            initMenuDTO.setPath(menuItem.getPath());
            initMenuDTO.setName(menuItem.getName());
            initMenuDTO.setComponent(menuItem.getComponent());
            initMenuDTO.setMeta(menuMataDTO);
            menus.add(initMenuDTO);
        });
        // 找到父节点
        for (InitMenuDto menu : menus) {
            if (ObjectUtil.isEmpty(menu.getPid())) {
                menu.setChildren(new ArrayList<InitMenuDto>());
                dataList.add(menu);
            }
        }
        // 根据父节点找到子节点
        for (InitMenuDto menu : dataList) {
            menu.getChildren().add(findInitMenuChildren(menu, menus));
        }
        return dataList;
    }

    private InitMenuDto findInitMenuChildren(InitMenuDto menu, List<InitMenuDto> menus) {
        menu.setChildren(new ArrayList<>());
        for (InitMenuDto item : menus) {
            if (menu.getId().equals(item.getPid())) {
                menu.getChildren().add(findInitMenuChildren(item, menus));
            }
        }
        return menu;
    }
}
