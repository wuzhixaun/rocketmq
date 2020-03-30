package com.wuzx.rocketmq.Listener;

import com.wuzx.rocketmq.constant.Constants;
import com.wuzx.rocketmq.constant.RocketMqConstant;
import com.wuzx.rocketmq.util.LogExceptionWapper;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: rocketmq
 * @description: 订单消息监听者
 * @author: wuzhixuan
 * @create: 2020-03-30 17:20
 **/
@Component
public class MqOrderListenerImpl implements MessageListenerConcurrently {
    private static final Logger LOGGER = LoggerFactory.getLogger(MqOrderListenerImpl.class);


    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            for (MessageExt msg : list) {
                // 消息解码
                String message = new String(msg.getBody());
                // 消息主题
                String topic = msg.getTopic();
                // 消息id
                String msgId = msg.getMsgId();
                // 消息消费次数
                int reconsumeTimes = msg.getReconsumeTimes();
                LOGGER.info(Constants.MESSAGE_CONSUMER_ORDER, message, String.format(RocketMqConstant.MESSAGE_CONSUMER_TIME, msgId, reconsumeTimes));

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        } catch (Exception e) {
            LOGGER.info("[秒杀订单消费者]消费异常,e={}", LogExceptionWapper.getStackTrace(e));
        }

        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }
}
