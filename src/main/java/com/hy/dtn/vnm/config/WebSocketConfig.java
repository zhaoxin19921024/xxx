package com.hy.dtn.vnm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author yjz
 * @version 1.0
 * @className WebSocketConfig
 * @date 2021/03/15 16:02
 * @description 描述
 * @note 说明
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /*
         * 订阅来自以下的消息
         */
        registry.enableSimpleBroker("/vnmGctxServer");
        /*
          客户端发送过来的消息，需要以”/send“为前缀，再经过Broker转发给响应的Controller
         */
        registry.setApplicationDestinationPrefixes("/send");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //路径“/stomp”被注册为STOMP断点，对外暴露，客户端通过该路径接入WebSocket服务
        registry.addEndpoint("/stomp").setAllowedOrigins("*").withSockJS();
    }
}
