package com.wuzx.rocketmq.protocol;

/**
 * @program: rocketmq
 * @description:
 * @author: wuzhixuan
 * @create: 2020-03-30 17:47
 **/
public abstract class BaseMessage {
    /**版本号，默认1.0*/
    private String version = "1.0";
    /**主题名*/
    private String topicName;

    public abstract String encode();

    public abstract void decode(String msg);

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "version='" + version + '\'' +
                ", topicName='" + topicName + '\'' +
                '}';
    }
}
