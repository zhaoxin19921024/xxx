package com.hy.dtn.vnm.monitor.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * t_jkrw_jkzb
 * @author 
 */
@Data
public class JkrwJkzbObj implements Serializable {
    /**
     * 查看数据字典 zblx
     */
    private String zbbs;

    private String rwbs;

    private static final long serialVersionUID = 1L;
}