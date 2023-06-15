package com.hl.admin.utils;


import cn.hutool.core.util.StrUtil;
import com.hl.model.ums.UmsMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {

    //构建树形结构
    public static List<UmsMenu> buildTree(List<UmsMenu> sysMenuList) {
        //创建集合封装最终数据
        List<UmsMenu> trees = new ArrayList<>();
        //遍历所有菜单集合
        for (UmsMenu sysMenu:sysMenuList) {
            if(StrUtil.isNotEmpty(sysMenu.getPid())) {
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    //从根节点进行递归查询，查询子节点
    private static UmsMenu findChildren(UmsMenu sysMenu, List<UmsMenu> treeNodes) {
        //数据初始化
        sysMenu.setChildren(new ArrayList<UmsMenu>());
        //遍历递归查找
        for (UmsMenu it:treeNodes) {
            //比对
            if((sysMenu.getId().equals(it.getPid()))) {
                if(sysMenu.getChildren()==null) {
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return sysMenu;
    }
}
