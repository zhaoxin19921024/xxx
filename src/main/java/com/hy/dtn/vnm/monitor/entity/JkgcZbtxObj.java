package com.hy.dtn.vnm.monitor.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author zhaoxin
 * @date 2021/12/7 14:12
 */
@Data
public class JkgcZbtxObj implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图形标识
     */
    private String txbs;

    /**
     *图形名称
     */
    private String txmc;

    /**
     * 工程标识
     */
    private String gcbs;

    /**
     * 指标标识
     */
    private Integer zbbs;

    /**
     * rwlx
     */
    private Integer rwlx;


    private List<String> rwbsList = new ArrayList<>();

}