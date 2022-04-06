package com.hy.dtn.vnm.monitor.serv.impl;


import cn.hutool.core.util.ArrayUtil;
import com.github.pagehelper.PageInfo;
import com.hy.dtn.vnm.biz.bo.JkrwRwxxBo;
import com.hy.dtn.vnm.biz.vo.JkgcZbServerVo;
import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.common.SystemConstant;
import com.hy.dtn.vnm.monitor.entity.JkrwJkzbObj;
import com.hy.dtn.vnm.monitor.entity.JkrwRwxxObj;
import com.hy.dtn.vnm.monitor.orm.mysql.dao.JkrwJkzbDao;
import com.hy.dtn.vnm.monitor.orm.mysql.dao.JkrwRwxxDao;
import com.hy.dtn.vnm.monitor.serv.MonitorBaseServ;
import com.hy.dtn.vnm.monitor.serv.intf.MonitorTaskIntf;
import com.hy.dtn.vnm.rpc.mo.BoRestResObj;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaoxin
 * @version 1.0
 * @className MonitorImpl
 * @date 2021/03/15 15:37
 * @description Service接口实现类
 * @note 说明
 */
@Service
public class MonitorTaskImpl extends MonitorBaseServ implements MonitorTaskIntf {


    @Resource
    JkrwRwxxDao jkrwRwxxDao;
    @Resource
    JkrwJkzbDao jkrwJkzbDao;

    /**
     * 新增监控任务
     *
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     * @author zhaoxin
     * @date 2021/12/8 14:37
     */
    @Override
    public Result<?> addJkrw(JkrwRwxxObj obj) {
        jkrwRwxxDao.insert(obj);
        if(ArrayUtil.isNotEmpty(obj.getRwzbList())){
            jkrwJkzbDao.insert(obj);
        }
        return Result.ok();
    }

    /**
     * 编辑监控任务
     *
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     * @author zhaoxin
     * @date 2021/12/8 14:37
     */
    @Override
    public Result<?> editJkrw(JkrwRwxxObj obj) {
        jkrwRwxxDao.updateByPrimaryKey(obj);
        if(ArrayUtil.isNotEmpty(obj.getRwzbList())){
            jkrwJkzbDao.deleteByPrimaryKey(obj.getRwbs());
            jkrwJkzbDao.insert(obj);
        }
        return Result.ok();
    }

    /**
     * 删除监控任务
     *
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     * @author zhaoxin
     * @date 2021/12/8 14:37
     */
    @Override
    public Result<?> delJkrw(JkrwRwxxObj obj) {
        jkrwRwxxDao.deleteByPrimaryKey(obj.getRwbs());
        return Result.ok();
    }

    /**
     * 查询监控任务列表
     *
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     * @author zhaoxin
     * @date 2021/12/8 14:37
     */
    @Override
    public Result<?> getJkrwList(JkrwRwxxObj obj) {
        List<JkrwRwxxBo> list = jkrwRwxxDao.getJkrwList(obj);
        return Result.ok(new PageInfo<>(list));
    }

    /**
     * 开始或停止任务
     *
     * @param vo
     * @return com.hy.dtn.vnm.common.Result<?>
     * @author zhaoxin
     * @date 2021/12/14 10:58
     */
    @Override
    @Transactional
    public Result<?> startOrStopTask(JkgcZbServerVo vo) {
        BoRestResObj boRestResObj = deployClient.deployServStartOrStopTask(vo.getTaskList(), vo.getStartOrStop());
        if (boRestResObj.getOptres() == SystemConstant.SERVER_RESULT_STATE_SUCCESS) {
            jkrwRwxxDao.updateRwztByRwbs(vo);
            return Result.ok();
        }
        return Result.error();
    }
}
