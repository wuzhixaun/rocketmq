package com.wuzx.rocketmq.protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * @program: rocketmq
 * @description: 订单结果通知协议
 * @author: wuzhixuan
 * @create: 2020-03-30 17:46
 **/
@Getter
@Setter
@Accessors(chain = true)
public class OrderMessageProtocol extends BaseMessage implements Serializable {

    /**
     * 订单号
     */
    private String orderId;
    /**
     * 用户下单手机号
     */
    private String userPhoneNo;
    /**
     * 商品id
     */
    private String prodId;
    /**
     * 用户交易金额
     */
    private String chargeMoney;

    private Map<String, String> header;
    private Map<String, String> body;


    @Override
    public String encode() {
        // 组装消息协议头
        ImmutableMap.Builder headerBuilder = new ImmutableMap.Builder<String, String>()
                .put("version", this.getVersion())
                .put("topicName", "");
        header = headerBuilder.build();

        body = new ImmutableMap.Builder<String, String>()
                .put("orderId", this.getOrderId())
                .put("userPhoneNo", this.getUserPhoneNo())
                .put("prodId", this.getProdId())
                .put("chargeMoney", this.getChargeMoney())
                .build();

        ImmutableMap<String, Object> map = new ImmutableMap.Builder<String, Object>()
                .put("header", header)
                .put("body", body)
                .build();

        // 返回序列化消息Json串
        String retString = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retString = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("ChargeOrderMsgProtocol消息序列化json异常", e);
        }
        return retString;
    }

    @Override
    public void decode(String msg) {
        Preconditions.checkNotNull(msg);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(msg);
            // header
            this.setVersion(root.get("header").get("version").asText());
            this.setTopicName(root.get("header").get("topicName").asText());
            // body
            this.setOrderId(root.get("body").get("orderId").asText());
            this.setUserPhoneNo(root.get("body").get("userPhoneNo").asText());
            this.setChargeMoney(root.get("body").get("chargeMoney").asText());
            this.setProdId(root.get("body").get("prodId").asText());
        } catch (IOException e) {
            throw new RuntimeException("ChargeOrderMsgProtocol消息反序列化异常", e);
        }
    }

}
