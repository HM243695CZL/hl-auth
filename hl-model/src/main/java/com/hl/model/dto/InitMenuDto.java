package com.hl.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 初始化菜单的数据传输对象
 */
@Data
public class InitMenuDto {

    private String id;

    private String pid;

    private String path;

    private String name;

    private String component;

    private MenuMataDto meta;

    private List<InitMenuDto> children;
}

