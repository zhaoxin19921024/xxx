package com.hy.dtn.vnm.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author suncm
 * @description 实现动态添加队列及绑定关系、动态添加监听。动态调整监听线程池大小、动态删除队列、动态取消监听
 * @date 2021/1/26 14:55
 */
//@Component
@Slf4j
//@Setter
//@Getter
public class DynamicRabbitmq {

    private final RabbitTemplate rabbitTemplate;

    @Lazy
    private final RabbitAdmin rabbitAdmin;

    public DynamicRabbitmq(RabbitTemplate rabbitTemplate, RabbitAdmin rabbitAdmin) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitAdmin = rabbitAdmin;
    }

    /**
     * 动态交换机默认名称
     */
    private static final String EXCHANGE = "hy.dynamic.exchange";

    /**
     * 默认死信交换机名称
     */
    private static final String DLX_EXCHANGE = "hy.dynamic.exchange.dlx";

    /**
     * 默认死信队列名称
     */
    private static final String DLX_QUEUE = "hy.dynamic.queue.dlx";

    /**
     * 默认死信绑定建
     */
    private static final String DLX_ROUTING = "hy.dynamic.routing.dlx";

    /**
     *
     */
    private static final Map<String, DirectMessageListenerContainer> CONTAINER_MAP = new ConcurrentHashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 动态添加队列及绑定关系
     *
     * @param queueName 队列名
     * @param exchange  交换机名称
     * @param needDlx   需要死信队列
     */
    public void addQueueAndExchange(String queueName, String exchange, boolean needDlx) {
        QueueInformation info = rabbitAdmin.getQueueInfo(queueName);
        if (null == info) {
            // 开始创建队列
            Map<String, Object> arguments = new HashMap<>(2);
            arguments.put("x-dead-letter-exchange", DLX_EXCHANGE);
            arguments.put("x-dead-letter-routing-key", DLX_ROUTING);
            Queue queue = new Queue(queueName, true, false, false, arguments);
            if (needDlx) {
                // 创建死信队列
                QueueInformation queueInfo = rabbitAdmin.getQueueInfo(DLX_QUEUE);
                if (null == queueInfo) {
                    Queue dlxQueue = new Queue(DLX_QUEUE);
                    DirectExchange dlxDirectExchange = new DirectExchange(DLX_EXCHANGE);
                    rabbitAdmin.declareQueue(dlxQueue);
                    rabbitAdmin.declareExchange(dlxDirectExchange);
                    rabbitAdmin.declareBinding(BindingBuilder.bind(dlxQueue).to(dlxDirectExchange).with(DLX_ROUTING));
                    log.info("创建死信【{}】队列成功", DLX_QUEUE);
                }
            }
            TopicExchange topicExchange = new TopicExchange(exchange);
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareExchange(topicExchange);
            rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(topicExchange).with(queueName));
        }

    }

    /**
     * 动态删除队列（队列有消息时不删除）
     *
     * @param queueName 队列名
     */
    public void deleteQueue(String queueName) {
        QueueInformation queueInfo = rabbitAdmin.getQueueInfo(queueName);
        if (null != queueInfo) {
            rabbitAdmin.deleteQueue(queueName);
            log.info("成功删除mq队列【{}】", queueName);
        }
    }

    /**
     * 动态添加队列监听及修改消费者线程池大小
     *
     * @param queueName   队列名
     * @param consumerNum 消费者线程数量
     * @param needDlx     需要死信队列
     */
    public void startListener(String queueName, int consumerNum, boolean needDlx, String type) {
        // 创建队列及绑定关系
        addQueueAndExchange(queueName, EXCHANGE, needDlx);
        // 创建消费者监控
        DirectMessageListenerContainer container = new DirectMessageListenerContainer(rabbitTemplate.getConnectionFactory());
        DirectMessageListenerContainer absentContainer = CONTAINER_MAP.putIfAbsent(queueName, container);
        if (null == absentContainer) {
            container.setQueueNames(queueName);
            container.setPrefetchCount(consumerNum);
            container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
            container.setConsumersPerQueue(consumerNum);
            MessageListenerAdapter adapter = new MessageListenerAdapter(applicationContext.getBean(type));
            container.setMessageListener(adapter);
            container.start();
        }
    }

    /**
     * 动态停止监听并删除队列
     *
     * @param queueName 队列名
     */
    public void stopListener(String queueName) {
        DirectMessageListenerContainer container = CONTAINER_MAP.get(queueName);
        if (null != container) {
            container.stop();
            container.destroy();
            CONTAINER_MAP.remove(queueName);
        }
        deleteQueue(queueName);
    }

    /**
     * 发送消息
     *
     * @param routingKey 路由名
     * @param data       数据
     */
    public void sendMsg(String routingKey, String data) {
        rabbitTemplate.convertAndSend(EXCHANGE, routingKey, data);
    }

}
