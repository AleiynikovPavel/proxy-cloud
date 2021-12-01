package com.sky.statistic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {

    @Autowired
    ProxyService proxyService;

    public Map<String, Long> getDistribution(String jwt, int reqCount) {
        List<String> ipSeries = new ArrayList<>(reqCount);
        for (int i = 0; i < reqCount; i++) {
            ipSeries.add(proxyService.getSourceIp(jwt));
        }
        return ipSeries.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

}
