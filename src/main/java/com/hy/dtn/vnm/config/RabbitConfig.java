package com.hy.dtn.vnm.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author suncm
 * @description rabbitmq 配置类
 * @date 2021/1/25 16:05
 */
@Configuration
public class RabbitConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            // 消息被打回的处理

        });
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            // 消息发送之后确认收到消息，回调信息
        });
        return rabbitTemplate;
    }


    /**
     * 初始化死信交换机
     *
     * @return 死信交换机
     */
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange("hy.dynamic.exchange.dlx");
    }

    /**
     * 初始化死信队列
     *
     * @return 死信队列
     */
    @Bean
    public Queue dlxQueue() {
        return new Queue("hy.dynamic.queue.dlx");
    }

    /**
     * 绑定死信队列
     *
     * @return 绑定关系
     */
    @Bean
    public Binding bindingDlx() {
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with("hy.dynamic.routing.dlx");
    }

    /**
     * 初始化主题交换机
     *
     * @return 主题交换机
     */
    @Bean
    public DirectExchange deviceExchange() {
        return new DirectExchange("hy.vnm.exchange");
    }

    /**
     * 初始化页面 APP 队列
     *
     * @return 队列
     */
    @Bean
    public Queue webVnmQueue() {
        Map<String, Object> arguments = new HashMap<>(3);
        arguments.put("x-dead-letter-exchange", "hy.dynamic.exchange.dlx");
        arguments.put("x-dead-letter-routing-key", "hy.dynamic.routing.dlx");
        arguments.put("x-message-ttl", 5000);
        return new Queue("hy.device.vnm.queue", true, false, false, arguments);
    }

    /**
     * 绑定页面 APP 队列
     *
     * @return 队列
     */
    @Bean
    public Binding webVnmBinding() {
        return BindingBuilder.bind(webVnmQueue()).to(deviceExchange()).with("hy.device.vnm.key");
    }
}
