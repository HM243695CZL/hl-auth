package com.hl.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasePageDto {

    @ApiModelProperty(value = "第几页")
    private Integer pageIndex;

    @ApiModelProperty(value = "每页几条")
    private Integer pageSize;
}
