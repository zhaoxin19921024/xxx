package com.hy.dtn.vnm.websocket;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yjz
 * @version 1.0
 * @className WebSocketClient
 * @date 2021/03/16 14:41
 * @description 描述
 * @note 说明
 */
@Slf4j
@Service
public class WebSocketClient{

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    /**
     * @param topic    对应监听的主题
     * @param sendData 待发送的数据
     * @return void
     * @description 方法描述
     * @methodName sendToTopic
     * @author yjz
     * @date 2021/03/16 16:17
     * @note 修改说明
     */
    public void sendToTopic(String topic, Object sendData) {
        try {
            messagingTemplate.convertAndSend(topic, JSONObject.toJSONString(sendData));
        } catch (Exception e) {
            log.error("发送订阅数据失败发送失败", e);
            e.printStackTrace();
        }
    }

}
