package com.hy.dtn.vnm.monitor.ctrl;

import com.github.pagehelper.PageHelper;
import com.hy.dtn.vnm.biz.vo.JkgcZbServerVo;
import com.hy.dtn.vnm.biz.vo.JkrwRwxxVo;
import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.monitor.entity.JkrwRwxxObj;
import com.hy.dtn.vnm.monitor.serv.intf.MonitorTaskIntf;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhaoxin
 * @version 1.0
 * @className MonitorCtrl
 * @date 2021/03/15 14:48
 * @description 主机监控接口
 * @note 说明
 */
@Api(tags = "监控任务")
@RequestMapping(value = {"/api/task"})
@RestController
@Slf4j
public class MonitorTaskCtrl {

    /**
     * 导入Service类
     */
    @Resource
    MonitorTaskIntf monitorTaskIntf;


    /**
     * @return java.lang.String
     * @description 前端通过Stomp发送数据过来, 请求单个详细信息
     * @methodName say
     * @author yjz
     * @date 2021/03/16 15:21
     * @note 修改说明
     */
    @ApiOperation(value = "新增监控任务")
    @PostMapping("/addJkrw")
    public Result<?> addJkrw(@RequestBody JkrwRwxxVo vo) {
        JkrwRwxxObj obj = vo.toObj();
        return monitorTaskIntf.addJkrw(obj);
    }

    /**
     * @return java.lang.String
     * @description 前端通过Stomp发送数据过来, 请求单个详细信息
     * @methodName say
     * @author yjz
     * @date 2021/03/16 15:21
     * @note 修改说明
     */
    @ApiOperation(value = "编辑工程项目")
    @PostMapping("/editJkrw")
    public Result<?> editJkrw(@RequestBody JkrwRwxxVo vo) {
        JkrwRwxxObj obj = vo.toObj();
        return monitorTaskIntf.editJkrw(obj);
    }


    /**
     * @return java.lang.String
     * @description 前端通过Stomp发送数据过来, 请求单个详细信息
     * @methodName say
     * @author yjz
     * @date 2021/03/16 15:21
     * @note 修改说明
     */
    @ApiOperation(value = "删除监控任务")
    @PostMapping("/delJkrw")
    public Result<?> delJkrw(@RequestBody JkrwRwxxVo vo) {
        JkrwRwxxObj obj = vo.toObj();
        return monitorTaskIntf.delJkrw(obj);
    }

    /**
     * @return java.lang.String
     * @description 前端通过Stomp发送数据过来, 请求单个详细信息
     * @methodName say
     * @author yjz
     * @date 2021/03/16 15:21
     * @note 修改说明
     */
    @ApiOperation(value = "查询监控任务列表")
    @PostMapping("/getJkrwList")
    public Result<?> getJkrwList(@RequestBody JkrwRwxxVo vo) {
        // 开启分页
        PageHelper.startPage(vo.getCurrent(), vo.getPageSize());
        JkrwRwxxObj obj = vo.toObj();
        return monitorTaskIntf.getJkrwList(obj);
    }

    /**
     * 开始或停止任务
     * @author zhaoxin
     * @date 2021/12/14 11:00
     * @param vo 
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    @ApiOperation(value = "开始或停止任务")
    @PostMapping("/startOrStopTask")
    public Result<?> startOrStopTask(@RequestBody JkgcZbServerVo vo) {
        return monitorTaskIntf.startOrStopTask(vo);
    }


}
