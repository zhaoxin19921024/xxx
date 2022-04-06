package com.hy.dtn.vnm.monitor.serv.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hy.dtn.vnm.biz.vo.JkgcZbServerVo;
import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.common.SystemConstant;
import com.hy.dtn.vnm.monitor.ctrl.DeployClient;
import com.hy.dtn.vnm.monitor.entity.JkgcZbObj;
import com.hy.dtn.vnm.monitor.entity.JkgcZbtxObj;
import com.hy.dtn.vnm.monitor.entity.JksjObj;
import com.hy.dtn.vnm.monitor.orm.mysql.dao.JkgcGcxxDao;
import com.hy.dtn.vnm.monitor.orm.mysql.dao.JkgcZbrwDao;
import com.hy.dtn.vnm.monitor.orm.mysql.dao.JkgcZbtxDao;
import com.hy.dtn.vnm.monitor.serv.MonitorBaseServ;
import com.hy.dtn.vnm.monitor.serv.intf.MonitorIntf;
import com.hy.dtn.vnm.rpc.mo.BoRestResObj;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoxin
 * @version 1.0
 * @className MonitorImpl
 * @date 2021/03/15 15:37
 * @description Service接口实现类
 * @note 说明
 */
@Service
public class MonitorImpl extends MonitorBaseServ implements MonitorIntf {

    @Resource
    JkgcGcxxDao jkgcZbDao;

    @Resource
    JkgcZbtxDao jkgcZbtxDao;

    @Resource
    JkgcZbrwDao jkgcZbrwDao;


    /**
     * 新增工程项目
     *
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     * @author zhaoxin
     * @date 2021/12/7 14:27
     */
    @Override
    public Result<?> addJkgczb(JkgcZbObj obj) {
        //新增项目工程
        jkgcZbDao.insert(obj);
//        //新增展示指标
//        jkgcZbtxDao.insert(obj);
        return Result.ok();
    }

    /**
     * 编辑工程项目
     *
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     * @author zhaoxin
     * @date 2021/12/7 14:27
     */
    @Override
    public Result<?> editJkgczb(JkgcZbObj obj) {
        //编辑项目工程
        jkgcZbDao.updateByPrimaryKey(obj);
//        if( ArrayUtil.isNotEmpty(obj.getZbbsList()) ){
//            //删除展示指标
//            jkgcZbtxDao.deleteByPrimaryKey(obj.getGcbs());
//            //新增展示指标
//            jkgcZbtxDao.insert(obj);
//        }
        return Result.ok();
    }

    /**
     * 删除工程项目
     *
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     * @author zhaoxin
     * @date 2021/12/7 14:28
     */
    @Override
    public Result<?> delJkgczb(JkgcZbObj obj) {
        if(StrUtil.isEmpty(obj.getGcbs())){
            return Result.error("请选择需删除的工程");
        }
        jkgcZbDao.deleteByPrimaryKey(obj.getGcbs());
        return Result.ok();
    }

    /**
     * 获取工程项目列表
     *
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     * @author zhaoxin
     * @date 2021/12/7 14:29
     */
    @Override
    public Result<?> getJkgczbList(JkgcZbObj obj) {
        List<JkgcZbObj> list = jkgcZbDao.getJkgcList(obj);
        return Result.ok(list);
    }

    /**
     * 新增监控指标图形
     *
     * @return com.hy.dtn.vnm.common.Result<?>
     * @author zhaoxin
     * @date 2021/12/10 14:50
     */
    @Override
    public Result<?> addJkzbtx(JkgcZbtxObj obj) {
        jkgcZbtxDao.insert(obj);
        if(ArrayUtil.isNotEmpty(obj.getRwbsList())){
            jkgcZbrwDao.insert(obj);
        }
        return Result.ok();
    }

    /**
     * 获取工程信息图形集合
     *
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     * @author zhaoxin
     * @date 2021/12/10 15:36
     */
    @Override
    public Result<?> getGcxxTxList(JkgcZbtxObj obj) {
        Map<String, Object> map = new HashMap<>();
        Map<String, List<JksjObj>> tempMap = new HashMap<>();
        List<JkgcZbtxObj> list = jkgcZbtxDao.selectByGcbs(obj.getGcbs());
        BoRestResObj boRestResObj = deployClient.deployServGetGctxData(obj.getGcbs());
        map.put("list",list);
        if (boRestResObj.getOptres() == SystemConstant.SERVER_RESULT_STATE_SUCCESS) {
            JSONObject jsonObj = JSONObject.parseObject(new String(boRestResObj.getResObj()));
            String gcbs = jsonObj.get("gcbs").toString();
            Map<String, String> jsonMap = JSONObject.toJavaObject((JSON) jsonObj.get("txMap"), Map.class);
            for(String key : jsonMap.keySet()){
                List<JksjObj> tempList = JSONObject.parseArray(JSONObject.toJSONString(jsonMap.get(key)),JksjObj.class);
                for (JksjObj o : tempList) {
                    System.out.println(o);
                    long l = Long.valueOf(Math.round(Float.valueOf(o.getTime())))*1000;
                    System.out.println(DateUtil.date(l));
                    o.setTime(DateUtil.date(l).toString());
                }
                tempMap.put(key,tempList);
                System.out.println("接收到的数据"+tempList);
            }
        }
        map.put("tempMap",tempMap);
        return Result.ok(map);
    }

    /**
     * 獲取圖形數據
     *
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     * @author zhaoxin
     * @date 2021/12/10 16:14
     */
    @Override
    public Result<?> getGcTxDataFromServer(JkgcZbServerVo obj) {
        BoRestResObj boRestResObj = deployClient.deployServGetTxData(obj.getGcbs(),obj.getStartOrStop());
        if (boRestResObj.getOptres() == SystemConstant.SERVER_RESULT_STATE_SUCCESS) {
            return Result.ok();
        }
        return Result.error();
    }

    /**
     * 删除工程项目图形
     *
     * @param obj
     * @return com.hy.dtn.vnm.common.Result<?>
     * @author zhaoxin
     * @date 2021/12/13 15:20
     */
    @Override
    public Result<?> delGcTxData(JkgcZbtxObj obj) {
        jkgcZbtxDao.deleteByPrimaryKey(obj.getTxbs());
        return Result.ok();
    }

    /**
     * 
     * @author zhaoxin
     * @date 2021/12/15 10:44
     * @param obj 
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    @Override
    public Result<?> deployServGetGctxData(JkgcZbServerVo obj) {
        BoRestResObj boRestResObj = deployClient.deployServGetGctxData(obj.getGcbs());
        if (boRestResObj.getOptres() == SystemConstant.SERVER_RESULT_STATE_SUCCESS) {
            return Result.ok();
        }
        return Result.error();
    }

}
