package com.hy.dtn.vnm.user.orm.mysql.model;

import java.io.Serializable;

public class DictItemObjKey implements Serializable {
    private String zdbs;

    private String tmbs;

    private static final long serialVersionUID = 1L;

    public String getZdbs() {
        return zdbs;
    }

    public void setZdbs(String zdbs) {
        this.zdbs = zdbs == null ? null : zdbs.trim();
    }

    public String getTmbs() {
        return tmbs;
    }

    public void setTmbs(String tmbs) {
        this.tmbs = tmbs == null ? null : tmbs.trim();
    }
}