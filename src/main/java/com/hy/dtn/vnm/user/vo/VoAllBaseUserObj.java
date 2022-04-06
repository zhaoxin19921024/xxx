package com.hy.dtn.vnm.user.vo;

import cn.hutool.json.JSONObject;
import com.hy.dtn.vnm.user.bo.BoAllUserObj;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className VoAllBaseUserObj
 * @date 2021/03/10 16:19
 * @description 基础的前端用户全量信息类
 * @note 说明
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "基础的前端用户全量信息类")
@Data
@NoArgsConstructor
public class VoAllBaseUserObj extends VoBaseUserObj {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 8723940607619946145L;

    @ApiModelProperty(value = "菜单列表", name = "menuList")
    private List<JSONObject> menuList = new ArrayList<>();
    
    /**
     * @description 构造器
     * @methodName VoAllBaseUserObj
     * @author yjz
     * @date 2021/03/10 16:23
     * @param logUser
     * @note 修改说明
     */
    public VoAllBaseUserObj(BoAllUserObj logUser) {
        super(logUser);
    }
}
