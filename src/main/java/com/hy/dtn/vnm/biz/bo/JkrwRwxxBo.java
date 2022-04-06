package com.hy.dtn.vnm.biz.bo;

import cn.hutool.core.util.ArrayUtil;
import com.hy.dtn.vnm.common.PagingCondition;
import com.hy.dtn.vnm.monitor.entity.JkrwRwxxObj;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * t_jkrw_rwxx
 * @author 
 */
@Data
public class JkrwRwxxBo extends PagingCondition implements Serializable {

    private String rwbs;

    private String rwmc;

    /**
     * 任务类型  参考数据字典表 rwlx
     */
    private String rwlx;

    private String sbip;

    private Long sbdk;

    private String rwms;

    /**
     * 任务状态  任务状态 （0 关闭 1开启）
     */
    private Short rwzt;

    private List<JkrwJkzbBo> zbList;


    private static final long serialVersionUID = 1L;



}