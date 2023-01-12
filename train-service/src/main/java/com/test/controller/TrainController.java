package com.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.entity.Train;
import com.test.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TrainController {

    @Resource
    TrainService service;

    @Autowired
    HttpServletRequest request;

    // 新增，用于从 TicketController 访问
    @PostMapping("/train/return-ticket/{trainId}")
    public void returnTicket(@PathVariable("trainId") String tid) {
        System.out.println("\n");
        logTitle("TrainController::returnTicket()");
        service.returnTicket(tid);
    }

    // 新增，用于从 TicketController 访问
    @PostMapping("/train/try-selling-ticket/{trainId}")
    public void trySellingTicket(@PathVariable("trainId") String tid) {
        System.out.println("\n");
        logTitle("TrainController::trySellingTicket()");
        service.trySellingTicket(tid);  // remain 约束 unsigned，抛异常
    }

    // 新增，用于从 TicketController 访问
    @RequestMapping("/train/{trainId}")
    public Train queryTrain(@PathVariable("trainId") String tid) {
        System.out.println("\n");
        logTitle("TrainController::queryTrain()");
        logContent("车次id: " + tid);
        Train train = service.getTrainById(tid);
        logContent("Train: " + train);
        return train;
    }

    @GetMapping("/allTrain")
    public Map<String, Object> allTrain() {
        //System.out.println(reqBody);
        Map<String, Object> response = new HashMap<>();
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
                    put("route", stationsSplit[0] + "-" + stationsSplit[stationsSplit.length - 1]);
                    put("capacity", train.getCapacity());
                    put("startTime", train.getStartTime());
                    put("frequency", train.getFrequency());
                    put("description", stations);
                }
            });
        }

        response.put("data", data);
        //System.out.println(response);
        return response;
    }

    @PostMapping("/addTrain")
    public Map<String, Object> addTrain(@RequestBody String reqBody) {
        System.out.println(reqBody);
        JSONObject jsonObject = JSONObject.parseObject(reqBody);
        Map<String, Object> response = new HashMap<>();
        String tid = jsonObject.getString("trainId");
        String stations = jsonObject.getString("stations");
        int capacity = Integer.parseInt(jsonObject.getString("capacity"));
        String startTime = jsonObject.getString("startTime").substring(0, 10);
        String frequency = jsonObject.getString("frequency");
        Train curTrain = service.getTrainById(tid);
        if (curTrain == null) {
            System.out.println(startTime);
            service.addTrain(tid, stations, capacity, startTime, frequency);
            response.put("success", true);
        } else {
            response.put("success", false);
        }
        return response;
    }

    @RequestMapping("/delTrain/{trainId}")
    public Map<String, Object> delTrain(@PathVariable(name = "trainId") String tid) {
        System.out.println("\n");
        logTitle("TrainController::delTrain()");
        logContent("train id: " + tid);
        Map<String, Object> response = new HashMap<>();
        try {
            service.deleteTrain(tid);
            response.put("success", true);
        } catch (Exception E) {
            response.put("success", false);
        }
        logContent("del: " +tid + " , " + response);
        return response;
    }

    @PostMapping("modifyTrain/{trainId}")
    public Map<String, Object> modifyTrain(@PathVariable(name = "trainId") String tid, @RequestBody String reqBody) {
        Map<String, Object> response = new HashMap<>();
        JSONObject jsonObject = JSONObject.parseObject(reqBody);
        Train curTrain = service.getTrainById(tid);
        int capacity = Integer.parseInt(jsonObject.getString("capacity"));
        String startTime = jsonObject.getString("startTime");
        String frequency = jsonObject.getString("frequency");
        if (curTrain != null) {
            //
            service.modifyTrain(tid, curTrain.getCapacity(), startTime, frequency);
            response.put("success", true);
        } else {
            response.put("success", false);
        }
        return response;
    }

    private static void logContent(String s) {
        System.out.println("\33[1m\33[35m" + "### " + s + " ###");
    }

    private static void logTitle(String s) {
        System.out.println("\33[1m\33[34m" + "### " + s + " ###");
    }
}
