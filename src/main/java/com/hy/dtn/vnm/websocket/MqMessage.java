package com.hy.dtn.vnm.websocket;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yjz
 * @version 1.0
 * @className Message
 * @date 2021/03/16 16:23
 * @description 描述
 * @note 说明
 */
@Data
public class MqMessage implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 316899545862085401L;

    /**
     * 订阅的主题
     */
    String topic;

    /**
     * 待发送的数据
     */
    Object sendData;
}
