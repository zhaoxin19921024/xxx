package com.hy.dtn.vnm.monitor.ctrl;



import com.hy.dtn.vnm.rpc.config.DeployConfig;
import com.hy.dtn.vnm.rpc.mo.BoRestResObj;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className DeployClient
 * @date 2021/01/01 14:22
 * @description 网络部署客户端
 * @note 说明
 */

@Component
public class DeployClient {

    @Resource
    private DeployConfig deployConfig;


    /**
     * 2.1	应用服务网络部署
     * get请求传输数据
     * 格式为：v2/deploy/{wlid}/{opt}
     *
     *
     * @param gcbs    是网络的ID，opt=0表示部署成功后不启动，1表示部署成功后再进行启动操作
     * @return 操作结果对象BoRestResObj, optres=0表示接收部署失败，1表示接收部署成功，msg为失败原因
     */
    public BoRestResObj deployServGetTxData(String gcbs, String startOrStop) {
        RestTemplate restTemplate = new RestTemplate();
        String urlstr = "http://" + deployConfig.getServer() + ":" + deployConfig.getPort() + "/workspace/" + gcbs + "/"+startOrStop;
        BoRestResObj entity = restTemplate.getForObject(urlstr, BoRestResObj.class);
        return entity;
    }

    /**
     * 开始或停止任务任务
     * @author zhaoxin
     * @date 2021/12/14 9:28
     * @param rwbsList
     * @param startOrStop
     * @return com.hy.dtn.vnm.rpc.mo.BoRestResObj
     */
    public BoRestResObj deployServStartOrStopTask(List<String> rwbsList, String startOrStop) {
        RestTemplate restTemplate = new RestTemplate();
        String urlstr = "http://" + deployConfig.getServer() + ":" + deployConfig.getPort() + "/tasks/"+startOrStop;
        BoRestResObj entity = restTemplate.postForObject(urlstr, rwbsList , BoRestResObj.class);
        return entity;
    }

    /**
     * 获取工程图形数据
     * @author zhaoxin
     * @date 2021/12/15 10:42
     * @param gcbs
     * @return com.hy.dtn.vnm.rpc.mo.BoRestResObj
     */
    public BoRestResObj deployServGetGctxData(String gcbs) {
        RestTemplate restTemplate = new RestTemplate();
        String urlstr = "http://" + deployConfig.getServer() + ":" + deployConfig.getPort() + "/workspace/" + gcbs + "/sample";
        BoRestResObj entity = restTemplate.getForObject(urlstr, BoRestResObj.class);
        return entity;
    }



}
