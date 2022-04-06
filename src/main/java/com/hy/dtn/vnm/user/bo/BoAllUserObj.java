package com.hy.dtn.vnm.user.bo;

import com.hy.dtn.vnm.user.orm.mysql.model.UserObj;
import lombok.Data;

/**
 * @author yjz
 * @version 1.0
 * @className
 * @date 2020/11/12 9:20
 * @description 用户信息
 * @note 说明
 */
@Data
public class BoAllUserObj extends BoBaseUserObj {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 4121597322894285184L;

    /**
     * 登录密码
     */
    private String dlmm;


    public BoAllUserObj() {

    }

    /**
     * @param user
     * @methodName BoUserObj
     * @author yjz
     * @description Dto数据转Bo数据
     * @date 2020/11/12
     * @returnParam
     * @note 修改说明
     */
    public BoAllUserObj(UserObj user) {
        this.setYhid(user.getYhid());
        this.setYhxm(user.getYhxm());
        this.setDlm(user.getDlm());
        this.setDlmm(user.getDlmm());
        this.setCsny(user.getCsny());
        this.setLxdh(user.getLxdh());
        this.setSfz(user.getSfz());
        this.setXb(user.getXb());
        this.setZw(user.getZw());
    }

    /**
     * @param
     * @methodName toDto
     * @author yjz
     * @description Bo数据转为Dto数据
     * @date 2020/11/12
     * @returnParam com.hy.dtn.ops.user.orm.mysql.model.UserObj
     * @note 修改说明
     */
    public UserObj toDto() {
        UserObj obj = new UserObj();
        obj.setYhid(this.getYhid());
        obj.setYhxm(this.getYhxm());
        obj.setDlm(this.getDlm());
        obj.setDlmm(this.getDlmm());
        obj.setCsny(this.getCsny());
        obj.setLxdh(this.getLxdh());
        obj.setSfz(this.getSfz());
        obj.setXb(this.getXb());
        obj.setZw(this.getZw());
        return obj;
    }


}
