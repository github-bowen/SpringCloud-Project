package com.test.service.client;

import com.test.entity.Train;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "train-service")
public interface TrainClient {
    @GetMapping("/train/{tid}")
    Train queryTrain(@PathVariable("tid") String tid);

    @PostMapping("/train/try-selling-ticket/{trainId}")
    boolean trySellingTicket(@PathVariable("trainId") String tid);

    @RequestMapping("/train/return-ticket/{trainId}")
    void returnTicket(@PathVariable("trainId") String tid);
}
