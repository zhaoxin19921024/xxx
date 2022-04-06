package com.hy.dtn.vnm.user.vo;

import com.hy.dtn.vnm.user.bo.BoDictItemObj;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yjz
 * @version 1.0
 * @className
 * @date 2020/11/11 17:49
 * @description 业务对象字典项信息类
 * @note 说明
 */
@ApiModel(description = "前端登录参数")
@Data
public class VoDictItemObj implements Serializable {

    private static final long serialVersionUID = 8717727064469923984L;

    @ApiModelProperty(value = "字典项内容", name = "label", example = "Router实际路由器")
    private String label;

    @ApiModelProperty(value = "字典项标识", name = "value", example = "Router")
    private String value;


    public VoDictItemObj() {
    }

    /**
     * @param boDictItemObj
     * @methodName VoDictItemObj
     * @author yjz
     * @description Bo转Vo
     * @date 2020/11/12 15:13
     * @returnParam
     * @note 修改说明
     */
    public VoDictItemObj(BoDictItemObj boDictItemObj) {
        this.label = boDictItemObj.getTmnr();
        this.value = boDictItemObj.getTmbs();
    }
}
