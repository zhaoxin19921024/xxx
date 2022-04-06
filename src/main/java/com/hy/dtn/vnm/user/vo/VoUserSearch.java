package com.hy.dtn.vnm.user.vo;

import com.hy.dtn.vnm.common.PagingCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author yjz
 * @version 1.0
 * @className VoUserSearch
 * @date 2021/03/08 14:18
 * @description 用户查询类
 * @note 说明
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ApiModel(description = "用户查询类")
public class VoUserSearch extends PagingCondition {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -1198259176298638285L;

    @ApiModelProperty(value = "用户姓名", name = "yhxm", example = "甘帅")
    private String yhxm;

    @ApiModelProperty(value = "身份证号", name = "sfz", example = "320123199511114567")
    private String sfz;

}
