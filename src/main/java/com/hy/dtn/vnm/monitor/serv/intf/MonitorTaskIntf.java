package com.hy.dtn.vnm.monitor.serv.intf;


import com.hy.dtn.vnm.biz.vo.JkgcZbServerVo;
import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.monitor.entity.JkgcZbObj;
import com.hy.dtn.vnm.monitor.entity.JkrwJkzbObj;
import com.hy.dtn.vnm.monitor.entity.JkrwRwxxObj;

/**
 * @author zhaoxin
 * @version 1.0
 * @className MonitorIntf
 * @date 2021/03/15 14:49
 * @description 主机监控Service接口
 * @note 说明
 */
public interface MonitorTaskIntf {

    /**
     * 新增监控任务
     * @author zhaoxin
     * @date 2021/12/8 14:37
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    Result<?> addJkrw(JkrwRwxxObj obj);

    /**
     * 编辑监控任务
     * @author zhaoxin
     * @date 2021/12/8 14:37
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    Result<?> editJkrw(JkrwRwxxObj obj);


    /**
     * 删除监控任务
     * @author zhaoxin
     * @date 2021/12/8 14:37
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    Result<?> delJkrw(JkrwRwxxObj obj);


    /**
     * 查询监控任务列表
     * @author zhaoxin
     * @date 2021/12/8 14:37
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    Result<?> getJkrwList(JkrwRwxxObj obj);


    /**
     * 开始或停止任务
     * @author zhaoxin
     * @date 2021/12/14 10:58
     * @param vo 
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    Result<?> startOrStopTask(JkgcZbServerVo vo);

}
