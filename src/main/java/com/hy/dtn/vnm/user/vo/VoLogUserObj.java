package com.hy.dtn.vnm.user.vo;


import com.hy.dtn.vnm.user.bo.BoLogUserObj;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yjz
 * @version 1.0
 * @className
 * @date 2020/11/11 17:50
 * @description 登录用户信息类
 * @note 说明
 */
@ApiModel(description = "返回前端的登录用户信息类")
@Data
public class VoLogUserObj {

    @ApiModelProperty(value = "用户iD", name = "yhid", example = "6ac9641944e1457ba61810eccef91092")
    private String yhid;

    @ApiModelProperty(value = "登录名", name = "dlm", example = "test")
    private String dlm;

    @ApiModelProperty(value = "用户姓名", name = "yhxm", example = "测试账号")
    private String yhxm;

    public VoLogUserObj() {
    }

    /**
     * @param bologuserobj
     * @methodName VoLogUserObj
     * @author yjz
     * @description Bo转Vo
     * @date 2020/11/12 11:31
     * @returnParam
     * @note 修改说明
     */
    public VoLogUserObj(BoLogUserObj bologuserobj) {
        this.setDlm(bologuserobj.getDlm());
        this.setYhid(bologuserobj.getYhid());
        this.setYhxm(bologuserobj.getYhxm());
    }
}
