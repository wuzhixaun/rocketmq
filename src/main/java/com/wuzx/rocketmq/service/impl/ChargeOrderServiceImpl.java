package com.wuzx.rocketmq.service.impl;

import com.wuzx.rocketmq.constant.CodeMsg;
import com.wuzx.rocketmq.constant.RocketMqConstant;
import com.wuzx.rocketmq.domin.ChargeOrderRequest;
import com.wuzx.rocketmq.domin.Result;
import com.wuzx.rocketmq.producer.MqOrderProducer;
import com.wuzx.rocketmq.protocol.OrderMessageProtocol;
import com.wuzx.rocketmq.service.ChargeOrderService;
import com.wuzx.rocketmq.util.LogExceptionWapper;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @program: rocketmq
 * @description: 下单接口实现类
 * @author: wuzhixuan
 * @create: 2020-03-30 18:38
 **/
@Service
public class ChargeOrderServiceImpl implements ChargeOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChargeOrderServiceImpl.class);

    @Autowired
    private MqOrderProducer mqOrderProducer;


    @Override
    public Result chargeOrderQuenue(ChargeOrderRequest request) {

        // 订单号生成,组装秒杀订单消息协议
        String orderId = UUID.randomUUID().toString();
        String phoneNo = request.getUserPhoneNum();

        OrderMessageProtocol msgProtocol = new OrderMessageProtocol();
        msgProtocol.setUserPhoneNo(phoneNo)
                .setProdId(request.getProdId())
                .setChargeMoney(request.getChargePrice())
                .setOrderId(orderId);

        String msgBody = msgProtocol.encode();
        LOGGER.info("秒杀订单入队,消息协议={}", msgBody);

        //获取消息生产者实例
        DefaultMQProducer orderProducer = mqOrderProducer.getOrderDefaultProducer();

        //组装消息体
        Message message = new Message(RocketMqConstant.ORDER_EVENT_TYPE.ORDER_EVENT.getTopic(), msgBody.getBytes());

        try {
            //发送消息
            orderProducer.send(message);
            return Result.success(CodeMsg.ORDER_INLINE, request);
        } catch (Exception e) {
            int sendRetryTimes = orderProducer.getRetryTimesWhenSendFailed();
            LOGGER.error("sendRetryTimes={},秒杀订单消息投递异常,下单失败.msgBody={},e={}",  sendRetryTimes, msgBody, LogExceptionWapper.getStackTrace(e));
        }
        return Result.error(CodeMsg.BIZ_ERROR);
    }
}
