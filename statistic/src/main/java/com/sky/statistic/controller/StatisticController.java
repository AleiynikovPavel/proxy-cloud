package com.sky.statistic.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sky.statistic.service.StatisticService;

@RestController
@CrossOrigin
public class StatisticController {

    @Autowired
    StatisticService statisticService;

    @GetMapping(value = "/distribution")
    public ResponseEntity<?> getDistributionStatistic(@RequestHeader("Authorization") String jwt, @RequestParam("req_count") int reqCount) {
        return ResponseEntity.ok(statisticService.getDistribution(jwt, reqCount));
    }

}