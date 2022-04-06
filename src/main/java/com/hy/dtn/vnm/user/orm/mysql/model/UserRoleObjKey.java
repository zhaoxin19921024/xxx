package com.hy.dtn.vnm.user.orm.mysql.model;

import java.io.Serializable;

public class UserRoleObjKey implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5935068947717423388L;

	private String yhid;

    private String jsdm;

    public String getYhid() {
        return yhid;
    }

    public void setYhid(String yhid) {
        this.yhid = yhid == null ? null : yhid.trim();
    }

    public String getJsdm() {
        return jsdm;
    }

    public void setJsdm(String jsdm) {
        this.jsdm = jsdm == null ? null : jsdm.trim();
    }
}