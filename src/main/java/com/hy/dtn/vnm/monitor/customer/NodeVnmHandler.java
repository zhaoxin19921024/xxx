package com.hy.dtn.vnm.monitor.customer;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hy.dtn.vnm.monitor.entity.JksjObj;
import com.hy.dtn.vnm.websocket.WebSocketClient;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yjz
 * @version 1.0
 * @className NodeAppHandler
 * @date 2021/03/24 11:11
 * @description 应用监控数据监听
 * @note 说明
 */
@Component
public class NodeVnmHandler {


    private final WebSocketClient webSocketClient;

    public NodeVnmHandler(WebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    /**
     * @param message 发送的数据
     * @param channel channel
     * @return void
     * @description 数据转换
     * @methodName onMessage
     * @author yjz
     * @date 2021/03/16 16:01
     * @note 修改说明
     */
    @RabbitListener(queues = "hy.device.vnm.queue")
    public void onMessage(Message message, Channel channel) throws IOException {
        //获取数据
//        String data = new String(message.getBody());

        JSONObject obj = JSONObject.parseObject(new String(message.getBody()));
        String gcbs = obj.get("gcbs").toString();

        //添加到队列中
//        JksjObj obj = JSON.parseObject(data, JksjObj.class);
        Map<String, String> jsonMap = JSONObject.toJavaObject((JSON) obj.get("txMap"), Map.class);
        Map<String, List<JksjObj>> map = new HashMap<>();
        for(String key : jsonMap.keySet()){

            List<JksjObj> list = JSONObject.parseArray(JSONObject.toJSONString(jsonMap.get(key)),JksjObj.class);
            for (JksjObj o : list) {
                System.out.println(o);
                long l = Long.valueOf(Math.round(Float.valueOf(o.getTime())))*1000;
                System.out.println(DateUtil.date(l));
                o.setTime(DateUtil.date(l).toString());
            }
            map.put(key,list);
            System.out.println("接收到的数据"+list);
        }

        System.out.println("接收到的数据"+gcbs);
        if (obj != null) {
//            //更换名称
//            this.replaceName(boApp);
            webSocketClient.sendToTopic("/vnmGctxServer/"+gcbs, map);
        }
//        //ACK确认
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }



}
