package com.hy.dtn.vnm.user.bo;

import java.io.Serializable;

public class BoDictObj implements Serializable {
    private String zdbs;

    private String mc;

    private Short zdlx;

    private static final long serialVersionUID = 1L;

    public String getZdbs() {
        return zdbs;
    }

    public void setZdbs(String zdbs) {
        this.zdbs = zdbs == null ? null : zdbs.trim();
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc == null ? null : mc.trim();
    }

    public Short getZdlx() {
        return zdlx;
    }

    public void setZdlx(Short zdlx) {
        this.zdlx = zdlx;
    }
}