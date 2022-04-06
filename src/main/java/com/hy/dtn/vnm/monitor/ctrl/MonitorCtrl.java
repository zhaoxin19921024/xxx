package com.hy.dtn.vnm.monitor.ctrl;

import com.hy.dtn.vnm.biz.vo.JkgcZbServerVo;
import com.hy.dtn.vnm.biz.vo.JkgcZbVo;
import com.hy.dtn.vnm.biz.vo.JkgcZbtxVo;
import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.monitor.entity.JkgcZbObj;
import com.hy.dtn.vnm.monitor.entity.JkgcZbtxObj;
import com.hy.dtn.vnm.monitor.serv.intf.MonitorIntf;
import com.hy.dtn.vnm.websocket.WebSocketClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
@Api(tags = "监控项展示")
@RequestMapping(value = {"/api/vnm"})
@RestController
@Slf4j
public class MonitorCtrl {

    /**
     * 导入Service类
     */
    @Resource
    MonitorIntf monitorIntf;


    /**
     * @return java.lang.String
     * @description 前端通过Stomp发送数据过来, 请求单个详细信息
     * @methodName say
     * @author yjz
     * @date 2021/03/16 15:21
     * @note 修改说明
     */
    @ApiOperation(value = "新增工程项目")
    @PostMapping("/addJkgczb")
    public Result<?> addJkgczb(@RequestBody JkgcZbVo vo) {
        JkgcZbObj obj = vo.toObj();
        return monitorIntf.addJkgczb(obj);
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
    @PostMapping("/editJkgczb")
    public Result<?> editJkgczb(@RequestBody JkgcZbVo vo) {
        JkgcZbObj obj = vo.toObj();
        return monitorIntf.editJkgczb(obj);
    }


    /**
     * @return java.lang.String
     * @description 前端通过Stomp发送数据过来, 请求单个详细信息
     * @methodName say
     * @author yjz
     * @date 2021/03/16 15:21
     * @note 修改说明
     */
    @ApiOperation(value = "删除工程项目")
    @PostMapping("/delJkgczb")
    public Result<?> delJkgczb(@RequestBody JkgcZbVo vo) {
        JkgcZbObj obj = vo.toObj();
        return monitorIntf.delJkgczb(obj);
    }

    /**
     * @return java.lang.String
     * @description 前端通过Stomp发送数据过来, 请求单个详细信息
     * @methodName say
     * @author yjz
     * @date 2021/03/16 15:21
     * @note 修改说明
     */
    @ApiOperation(value = "查询工程项目列表")
    @PostMapping("/getJkgczbList")
    public Result<?> getJkgczbList(@RequestBody JkgcZbVo vo) {
        JkgcZbObj obj = vo.toObj();
        return monitorIntf.getJkgczbList(obj);
    }

    /**
     * 
     * @author zhaoxin
     * @date 2021/12/10 14:58
 * @param vo
 * @return com.hy.dtn.vnm.common.Result<?>
     */
    @ApiOperation(value = "查询工程项目列表")
    @PostMapping("/addJkzbtx")
    public Result<?> addJkzbtx(@RequestBody JkgcZbtxVo vo) {
        JkgcZbtxObj obj = vo.toObj();
        return monitorIntf.addJkzbtx(obj);
    }

    /**
     * 查询工程图形列表
     * @author zhaoxin
     * @date 2021/12/10 16:04
     * @param vo 
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    @ApiOperation(value = "查询工程图形列表")
    @PostMapping("/getGcxxTxList")
    public Result<?> getGcxxTxList(@RequestBody JkgcZbtxVo vo) {
        JkgcZbtxObj obj = vo.toObj();
        return monitorIntf.getGcxxTxList(obj);
    }

    /**
     * 查询工程图形列表
     * @author zhaoxin
     * @date 2021/12/10 16:04
     * @param vo
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    @ApiOperation(value = "查询工程图形列表")
    @PostMapping("/getGcTxDataFromServer")
    public Result<?> getGcTxDataFromServer(@RequestBody JkgcZbServerVo vo) {
        return monitorIntf.getGcTxDataFromServer(vo);
    }

    /**
     * 删除工程图形
     * @author zhaoxin
     * @date 2021/12/10 16:04
     * @param vo
     * @return com.hy.dtn.vnm.common.Result<?>
     */
    @ApiOperation(value = "删除工程图形")
    @PostMapping("/delGcTxData")
    public Result<?> delGcTxData(@RequestBody JkgcZbtxVo vo) {
        JkgcZbtxObj obj = vo.toObj();
        return monitorIntf.delGcTxData(obj);
    }

}
