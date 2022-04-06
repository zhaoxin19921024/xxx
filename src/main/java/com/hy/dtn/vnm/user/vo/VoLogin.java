package com.hy.dtn.vnm.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yjz
 * @version 1.0
 * @className VoLogin
 * @date 2020/11/17 10:47
 * @description 登录参数
 * @note 说明
 */
@ApiModel(description = "前端登录参数")
@Data
public class VoLogin {

    @ApiModelProperty(value = "登录名", name = "dlm", example = "测试账号")
    private String dlm;

    @ApiModelProperty(value = "登录密码", name = "dlmm", example = "1a2b3c4d")
    private String dlmm;
}
