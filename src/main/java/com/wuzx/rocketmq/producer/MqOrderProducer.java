package com.wuzx.rocketmq.producer;

import com.wuzx.rocketmq.config.RocketMQNamesrvConfig;
import com.wuzx.rocketmq.constant.RocketMqConstant;
import com.wuzx.rocketmq.util.LogExceptionWapper;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @program: rocketmq
 * @description: 下单生产者
 * @author: wuzhixuan
 * @create: 2020-03-30 16:48
 **/
@Component
public class MqOrderProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqOrderProducer.class);

    @Autowired
    RocketMQNamesrvConfig mqNamesrvConfig;

    private DefaultMQProducer defaultMQProducer;
    /**
     * PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。
     * PostConstruct在构造函数之后执行，init（）方法之前执行。
     * PreDestroy（）方法在destroy（）方法知性之后执行
     */
    @PostConstruct
    public void preInit() {
        //实例化消息生产者Producer
        defaultMQProducer = new DefaultMQProducer(RocketMqConstant.ORDER_EVENT_TYPE.ORDER_EVENT.getProducerGroup());
        //设置NameServer的地址
        defaultMQProducer.setNamesrvAddr(mqNamesrvConfig.getNameSrvAddr());
        // 发送失败重试次数
        defaultMQProducer.setRetryTimesWhenSendFailed(3);
        //启动Producer实例
        try {
            defaultMQProducer.start();
        } catch (MQClientException e) {
            LOGGER.error("[订单生产者]--MqOrderProducer加载异常!e={}", LogExceptionWapper.getStackTrace(e));
            throw new RuntimeException("[秒杀订单生产者]--SecKillChargeOrderProducer加载异常!", e);
        }

    }

   /**
   * @Description: 获取订单消息生产者
   * @return: org.apache.rocketmq.client.producer.DefaultMQProducer
   * @Author: wuzhixuan
   * @Date: 2020/3/30
   */
    public DefaultMQProducer getOrderDefaultProducer() {
        return defaultMQProducer;
    }
}
