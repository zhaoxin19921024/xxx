package com.hy.dtn.vnm.rpc.client;

import com.hy.dtn.vnm.rpc.config.AssetsConfig;
import com.hy.dtn.vnm.rpc.mo.BoRestResObj;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author yjz
 * @version 1.0
 * @className AssetsClient
 * @date 2021/04/02 16:41
 * @description 描述
 * @note 说明
 */
@Component
public class AssetsClient {

    @Resource
    private AssetsConfig assetsConfig;


    /**
     * @param serverId 服务器id
     * @param serverIp 服务器ip
     * @return com.hy.dtn.ops.rpc.mo.BoRestResObj
     * @description 资产注册
     * @methodName register
     * @author yjz
     * @date 2021/04/02 16:45
     * @note 修改说明
     */
    public BoRestResObj register(String serverId, String serverIp) {
        RestTemplate restTemplate = new RestTemplate();
        String urlstr = "http://" + assetsConfig.getServer() + ":" + assetsConfig.getPort() + "/servers/register/" + serverId + "/" + serverIp;
        return restTemplate.getForObject(urlstr, BoRestResObj.class);
    }

    /**
     * @param serverId 服务器id
     * @return com.hy.dtn.ops.rpc.mo.BoRestResObj
     * @description 资产反注册
     * @methodName unRegister
     * @author yjz
     * @date 2021/04/02 16:45
     * @note 修改说明
     */
    public BoRestResObj unRegister(String serverId) {
        RestTemplate restTemplate = new RestTemplate();
        String urlstr = "http://" + assetsConfig.getServer() + ":" + assetsConfig.getPort() + "/servers/unregister/" + serverId;
        return restTemplate.getForObject(urlstr, BoRestResObj.class);
    }

}
