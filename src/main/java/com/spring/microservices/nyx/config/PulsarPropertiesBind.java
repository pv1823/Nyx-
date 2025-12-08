package com.spring.microservices.nyx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pulsar.producer")
public class PulsarPropertiesBind {
    private String productTopic;

    public void setProductTopic(String productTopic) {
        this.productTopic = productTopic;
    }

    public String getProductTopic() {
        return productTopic;
    }
}