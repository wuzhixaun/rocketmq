package com.wuzx.rocketmq.service;

import com.wuzx.rocketmq.domin.ChargeOrderRequest;
import com.wuzx.rocketmq.domin.Result;

/**
 * @program: rocketmq
 * @description: 下单接口
 * @author: wuzhixuan
 * @create: 2020-03-30 18:37
 **/
public interface ChargeOrderService {

    /**
    * @Description: 下单
    * @Param: [request]
    * @return: com.wuzx.rocketmq.domin.Result
    * @Author: wuzhixuan
    * @Date: 2020/3/30
    */
    public Result chargeOrderQuenue(ChargeOrderRequest request);
}
