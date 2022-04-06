package com.hy.dtn.vnm.biz.vo;

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
public class JkrwRwxxVo extends PagingCondition implements Serializable {

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

    private List<String> rwzbList;


    private static final long serialVersionUID = 1L;


    public JkrwRwxxObj toObj(){
        JkrwRwxxObj obj = new JkrwRwxxObj();
        obj.setRwbs(this.rwbs);
        obj.setRwlx(this.rwlx);
        obj.setRwmc(this.rwmc);
        obj.setRwms(this.rwms);
        obj.setRwzt(this.rwzt);
        obj.setSbip(this.sbip);
        obj.setSbdk(this.sbdk);
        if(ArrayUtil.isNotEmpty(this.rwzbList)){
            obj.getRwzbList().addAll(this.rwzbList);
        }
        return obj;
    }
}