package com.hy.dtn.vnm.monitor.serv.intf;

import com.hy.dtn.vnm.biz.vo.JkgcZbServerVo;
import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.monitor.entity.JkgcZbObj;
import com.hy.dtn.vnm.monitor.entity.JkgcZbtxObj;

/**
 * @author zhaoxin
 * @version 1.0
 * @className MonitorIntf
 * @date 2021/03/15 14:49
 * @description 主机监控Service接口
 * @note 说明
 */
public interface MonitorIntf {

    /**
     * 新增工程项目
     * @author zhaoxin
     * @date 2021/12/7 14:27
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    Result<?> addJkgczb(JkgcZbObj obj);

    /**
     * 编辑工程项目
     * @author zhaoxin
     * @date 2021/12/7 14:27
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    Result<?> editJkgczb(JkgcZbObj obj);

    /**
     * 删除工程项目
     * @author zhaoxin
     * @date 2021/12/7 14:28
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    Result<?> delJkgczb(JkgcZbObj obj);

    /**
     * 获取工程项目列表
     * @author zhaoxin
     * @date 2021/12/7 14:29
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    Result<?> getJkgczbList(JkgcZbObj obj);

    /**
     * 新增监控指标图形
     * @author zhaoxin
     * @date 2021/12/10 14:50
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    Result<?> addJkzbtx(JkgcZbtxObj obj);

    /**
     * 获取工程信息图形集合
     * @author zhaoxin
     * @date 2021/12/10 15:36
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    Result<?> getGcxxTxList(JkgcZbtxObj obj);

    /**
     * 獲取圖形數據
     * @author zhaoxin
     * @date 2021/12/10 16:14
     * @param obj 
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    Result<?> getGcTxDataFromServer(JkgcZbServerVo obj);

    /**
     * 删除工程项目图形
     * @author zhaoxin
     * @date 2021/12/13 15:20
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    Result<?> delGcTxData(JkgcZbtxObj obj);

    /**
     * 获取工程图形数据
     * @author zhaoxin
     * @date 2021/12/15 10:43
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    Result<?> deployServGetGctxData(JkgcZbServerVo obj);

}
