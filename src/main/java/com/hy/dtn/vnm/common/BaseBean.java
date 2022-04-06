package com.hy.dtn.vnm.common;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 实体公共字段基础类，用于实体JavaBean继承
 *
 * @author yjz
 * @version 1.0.0 2020-10-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseBean extends OverrideBeanMethods {
    private static final long serialVersionUID = 6101499067277733665L;

    @ApiModelProperty(value = "创建人，保存用户ID值")
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    @ApiModelProperty(value = "创建日期")
    @JsonFormat(timezone = "GMT+8", pattern = DatePattern.NORM_DATETIME_PATTERN)
    @TableField(fill = FieldFill.INSERT)
    private Date creationDate;

    @ApiModelProperty(value = "最后修改人，保存用户ID值")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    @ApiModelProperty(value = "最后修改日期")
    @JsonFormat(timezone = "GMT+8", pattern = DatePattern.NORM_DATETIME_PATTERN)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    @ApiModelProperty(value = "删除标记，字典数据，例如：0：已删除、1：未删除", example = "1")
    @NotNull(message = "删除标记不能为空！")
    @Size(min = 0, max = 1, message = "删除标记必需是{min}或{max}的一位正整数！")
    @TableLogic
    private Integer deleteFlag;
}
