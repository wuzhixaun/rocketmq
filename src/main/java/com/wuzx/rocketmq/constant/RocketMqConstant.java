package com.wuzx.rocketmq.constant;

/**
 * @program: rocketmq
 * @description: 常量配置，例如topic
 * @author: wuzhixuan
 * @create: 2020-03-30 16:35
 **/
public class RocketMqConstant {

    public final static String MESSAGE_CONSUMER_TIME = ",msgId=%s,reconsumeTimes=%d";



    public enum ORDER_EVENT_TYPE {
        /**
         * 消息事件订阅
         */
        ORDER_EVENT("ORDER_WUZX","WUZX_PRODUCER_GROOUP","WUZX_CONSUMER_GROOUP","订单消息事件"),
        ;
        /**消息主题*/
        private String topic;
        /**生产者组*/
        private String producerGroup;
        /**消费者组*/
        private String consumerGroup;
        /**消息描述*/
        private String desc;

        ORDER_EVENT_TYPE(String topic, String producerGroup, String consumerGroup, String desc) {
            this.topic = topic;
            this.producerGroup = producerGroup;
            this.consumerGroup = consumerGroup;
            this.desc = desc;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getProducerGroup() {
            return producerGroup;
        }

        public void setProducerGroup(String producerGroup) {
            this.producerGroup = producerGroup;
        }

        public String getConsumerGroup() {
            return consumerGroup;
        }

        public void setConsumerGroup(String consumerGroup) {
            this.consumerGroup = consumerGroup;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }


}
