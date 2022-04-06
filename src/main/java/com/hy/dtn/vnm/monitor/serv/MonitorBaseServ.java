package com.hy.dtn.vnm.monitor.serv;

import com.hy.dtn.vnm.monitor.ctrl.DeployClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yjz
 * @version 1.0
 * @className MonitorBaseServ
 * @date 2021/03/15 14:47
 * @description 注入相关依赖
 * @note 说明
 */

@Service
public class MonitorBaseServ {


    @Resource
    protected DeployClient deployClient;

}
