package com.hy.dtn.vnm.user.bo;


import com.hy.dtn.vnm.user.orm.mysql.model.UserObj;
import lombok.Data;

/**
 * @author yjz
 * @version 1.0
 * @className
 * @date 2020/11/11 17:50
 * @description 登录用户信息类
 * @note 说明
 */
@Data
public class BoLogUserObj {
    /**
     * 用户iD
     */
    private String yhid;

    /**
     * 登录名
     */
    private String dlm;

    /**
     * 用户姓名
     */
    private String yhxm;

    /**
     * 登录密码
     */
    private String dlmm;

    public BoLogUserObj() {
    }

    /**
     * @param
     * @methodName BoLogUserObj
     * @author yjz
     * @description Dto转为Bo
     * @date 2020/11/12 11:27
     * @returnParam
     * @note 修改说明
     */
    public BoLogUserObj(UserObj user) {
        this.setDlm(user.getDlm());
        this.setYhid(user.getYhid());
        this.setYhxm(user.getYhxm());
    }
}
