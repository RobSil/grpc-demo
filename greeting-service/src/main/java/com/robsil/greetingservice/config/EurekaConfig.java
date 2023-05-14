package com.robsil.greetingservice.config;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EurekaConfig implements BeanPostProcessor {

    @Value("${grpc.server.port}")
    private int grpcPort;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        if (beanName.equals("eurekaInstanceConfigBean")) {
//            EurekaInstanceConfigBean configBean = (EurekaInstanceConfigBean) bean;
//            Map<String, String> map = new HashMap<>(configBean.getMetadataMap());
//            map.put("gRPC_port", String.valueOf(grpcPort));
//            configBean.setMetadataMap(map);
//
//            return bean;
//        }

        return bean;
    }
}
