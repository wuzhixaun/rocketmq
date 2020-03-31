package com.wuzx.rocketmq.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: rocketmq
 * @description: RocketMqNameSrv地址管理中心
 * @author: wuzhixuan
 * @create: 2020-03-30 16:17
 **/
@Component
public class RocketMQNamesrvConfig {

    @Value("${namesrvAddr}")
    private String nameSrvAddr;

    public String getNameSrvAddr() {

        String envType = System.getProperty("envType");
        System.out.println(envType);
        if (StringUtils.isBlank(envType)) {
            throw new IllegalArgumentException("please insert envType");
        }
        switch (envType) {
            case "dev" : {
                return nameSrvAddr;
            }
            default : {
                throw new IllegalArgumentException("please insert right envType, dev");
            }
        }

    }


}
