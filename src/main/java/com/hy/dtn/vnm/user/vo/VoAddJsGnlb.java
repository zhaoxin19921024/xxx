package com.hy.dtn.vnm.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className VoAddJsGnlb
 * @date 2020/11/18 10:55
 * @description 前台新增角色功能列表
 * @note 说明
 */
@ApiModel(description = "前台新增角色功能列表")
@Data
public class VoAddJsGnlb implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -8714675914076861833L;

    @ApiModelProperty(value = "角色代码", name = "jsdm", example = "xxxxxxx")
    private String jsdm;

    @ApiModelProperty(value = "功能模块代码集合", name = "gnlist")
    private List<String> gnlist;
}
