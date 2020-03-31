package com.wuzx.rocketmq.consumer;

import com.wuzx.rocketmq.config.RocketMQNamesrvConfig;
import com.wuzx.rocketmq.constant.RocketMqConstant;
import com.wuzx.rocketmq.util.LogExceptionWapper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @program: rocketmq
 * @description: 下单消费者
 * @author: wuzhixuan
 * @create: 2020-03-30 16:49
 **/
@Service
public class MqOrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqOrderConsumer.class);

    @Autowired
    private RocketMQNamesrvConfig mqNamesrvConfig;

    private DefaultMQPushConsumer defaultMQPushConsumer;

    @Resource(name = "mqOrderListenerImpl")
    private MessageListenerConcurrently messageListener;

    @PostConstruct
    public void preInit() {
        // 实例化消息消费者
        defaultMQPushConsumer = new DefaultMQPushConsumer(RocketMqConstant.ORDER_EVENT_TYPE.ORDER_EVENT.getConsumerGroup());
        // 设置NameServer的地址
        defaultMQPushConsumer.setNamesrvAddr(mqNamesrvConfig.getNameSrvAddr());
        // 从头开始消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 消费模式:集群模式
        // 集群：同一条消息 只会被一个消费者节点消费到
        // 广播：同一条消息 每个消费者都会消费到
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        // 注册监听器
        defaultMQPushConsumer.setMessageListener(messageListener);
        // 设置每次拉取的消息量，默认为1
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);

        try {
            defaultMQPushConsumer.subscribe(RocketMqConstant.ORDER_EVENT_TYPE.ORDER_EVENT.getTopic(), "*");
            // 启动消费者
            defaultMQPushConsumer.start();

            LOGGER.info("----订单消费者-启动--");
        } catch (MQClientException e) {
            LOGGER.error("[订单消费者]--MqOrderProducer加载异常!e={}", LogExceptionWapper.getStackTrace(e));
            throw new RuntimeException("[订单消费者]--加载异常!", e);
        }
    }

    @PreDestroy
    public void shutDownConsumer() {
        defaultMQPushConsumer.shutdown();
        LOGGER.info("----订单消费者-关闭--");
    }

}
