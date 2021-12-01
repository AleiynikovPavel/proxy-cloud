package com.sky.statistic.service;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.sky.statistic.config.ProxyCustomizer;

@Service
public class ProxyService {

    @Value("${my.proxy.url}")
    public String url;
    @Value("${my.proxy.port}")
    public Integer port;
    RestTemplate restTemplate;

    @PostConstruct
    void init() {
        this.restTemplate = new RestTemplateBuilder(new ProxyCustomizer(url, port)).build();
    }

    public String getSourceIp(String jwt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Authorization", jwt);
        var result = restTemplate.exchange("http://api.ipify.org?format=json",
                HttpMethod.GET, new HttpEntity<String>(headers), JsonNode.class);
        return Objects.requireNonNull(result.getBody()).get("ip").asText();
    }

}
