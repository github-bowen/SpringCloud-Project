package com.test.controller;

import com.test.entity.Train;
import com.test.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class TrainController {

    @Resource
    TrainService service;

    @Autowired
    HttpServletRequest request;

    @RequestMapping("/allTrain")
    public Map<String ,Object> allTrain(@RequestBody String reqBody) {
        System.out.printf(reqBody);
        Map<String ,Object> response = new HashMap<>();
        List<Map<String, Object>> data = new ArrayList<>();
        response.put("success", true);

        List<Train> trains = service.getAllTrains();
        for (int i = 0; i < trains.size(); ++i) {
            final int index = i;
            final Train train = trains.get(index);
            final String stations = train.getStations();
            String[] stationsSplit = stations.split("[ -_,]");
            data.add(new HashMap<String, Object>() {
                {
                    put("key", index + 1);
                    put("trainId", train.getTid());
                    put("route", stationsSplit[0]);
                    put("capacity", train.getCapacity());
                    put("startTime", train.getStartTime());
                    put("frequency", train.getFrequency());
                    put("description", stationsSplit[stationsSplit.length - 1]);
                }
            });
        }

        response.put("data", data);
        return response;
    }



}
