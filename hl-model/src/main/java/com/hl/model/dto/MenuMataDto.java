package com.hl.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 初始化菜单的meta数据传输对象
 */
@Data
public class MenuMataDto {

    private String title;

    private String isLink;

    private Boolean isHide;

    private Boolean isKeepAlive;

    private Boolean isAffix;

    private Boolean isIframe;

    private List<String> roles;

    private String icon;
}
