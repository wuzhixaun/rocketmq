package com.wuzx.rocketmq.controller;

import com.wuzx.rocketmq.domin.ChargeOrderRequest;
import com.wuzx.rocketmq.domin.Result;
import com.wuzx.rocketmq.service.ChargeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: rocketmq
 * @description: 订单生成
 * @author: wuzhixuan
 * @create: 2020-03-30 18:20
 **/
@RestController
@RequestMapping("api/order/")
public class OrderChargeController {

    @Autowired
    private ChargeOrderService orderService;


    @PostMapping(value = "charge")
    public Result chargeOrder(ChargeOrderRequest orderRequest) {
        return orderService.chargeOrderQuenue(orderRequest);
    }
}
