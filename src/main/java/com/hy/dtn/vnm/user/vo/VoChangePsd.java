package com.hy.dtn.vnm.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yjz
 * @version 1.0
 * @className VoChangePsd
 * @date 2020/11/17 11:22
 * @description 描述
 * @note 说明
 */
@Data
public class VoChangePsd {
    @ApiModelProperty(value = "32位用户id", name = "yhid", example = "xxxxxx")
    private String yhid;

    @ApiModelProperty(value = "修改后的密码", name = "dlmm", example = "1a2b3c4d")
    private String dlmm;
}
