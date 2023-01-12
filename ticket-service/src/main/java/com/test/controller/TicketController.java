package com.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.entity.*;
import com.test.service.TicketService;
import com.test.service.client.TrainClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TicketController {

//    public static final String TRAIN_URL = "http://localhost:8103";
//
//    public static final String USER_URL = "http://localhost:8101";

    @Resource
    TicketService service;
    @Resource
    TrainClient trainClient;

    @Autowired
    HttpServletRequest request;

    @Autowired
    private RestTemplate restTemplate;

    // 买票
    @PostMapping("/addTicket/{trainId}")
    public Map<String, Object> addTicket(@PathVariable(name = "trainId") String trainId,
                                         @RequestBody String reqBody) {
        Map<String, Object> response = new HashMap<>();
        JSONObject jsonObject = JSONObject.parseObject(reqBody);
        String startStation = jsonObject.getString("beginStation");
        String endStation = jsonObject.getString("endStation");

//        // 从 TrainController 获取车次信息
//        String queryTrainUrl = TRAIN_URL + "/train/" + trainId;
//        Train train = restTemplate.getForObject(queryTrainUrl, Train.class);

        // 做两件事：
        // db_train 里面，先尝试对 train 的 remain 减一
        // 如果成功，再向 db_ticket 里面插入新的 ticket

        try {
            //String trySellingTicketUrl = TRAIN_URL + "/train/try-selling-ticket/" + trainId;
            //restTemplate.postForObject(trySellingTicketUrl, null, JSONObject.class);
            trainClient.trySellingTicket(trainId);
        } catch (Exception e) {
            response.put("success", false);
            return response;
        }

        // 购票成功，创建新的订单 ticket
        String token = request.getHeader("Authorization");
        try {
            service.addTicket(token, trainId, startStation, endStation);
        } catch (Exception e) {
            response.put("success", false);
            return response;
        }
        response.put("success", true);
        return response;
    }

    // 退票
    @GetMapping("/delTicket/{ticketId}")
    public Map<String, Object> delTicket(@PathVariable(name = "ticketId") String ticketId) {

        Ticket ticket = service.getTicketByTicketId(ticketId);
        //String returnTicketUrl = TRAIN_URL + "/train/return-ticket/" + ticket.getTrainId();
        //restTemplate.postForObject(returnTicketUrl, null, JSONObject.class);
        try {
            trainClient.returnTicket(ticketId);
            service.deleteTicket(Integer.parseInt(ticketId));
            return new HashMap<String, Object>() {{
                put("success", true);
            }};
        }catch (Exception e) {
            return new HashMap<String, Object>() {{
                put("success", false);
            }};
        }
    }

    // 获取个人车票
    // FIXME: 文档中定义的返回参数似乎只能返回一张票，这里修改如下：
    /**
     * 返回格式：
     * {
     * success:,
     * data: [{
     *      ticketId:,
     *      beginStation:,
     *      endStation:,
     *      trainId:,
     *      startTime:,
     *      description:详细路径,
     * }, {
     *      ticketId:,
     *      beginStation:,
     *      endStation:,
     *      trainId:,
     *      startTime:,
     *      description:详细路径,
     * }]
     */
    @RequestMapping("/myTicket")
    public Map<String, Object> userTickets() {
        Map<String, Object> response = new HashMap<>();
        String token = request.getHeader("Authorization");
        System.out.println("myTicket: token is :" + token);
        System.out.println(service.getTicketByTicketId(token));
        List<Map<String, Object>> data = service.getTicketsByUserId(
                token).stream().map(ticket ->
        {
            assert ticket != null;
            //final String queryTrainUrl = TRAIN_URL + "/train/" + ticket.getTrain_id();
            //final Train train = restTemplate.getForObject(queryTrainUrl, Train.class);
            final Train train = trainClient.queryTrain(ticket.getTrain_id());
            return new HashMap<String, Object>() {{
                put("ticketId", ticket.getTicket_id());
                put("beginStation", ticket.getStartStation());
                put("endStation", ticket.getEndStation());
                put("trainId", ticket.getTrain_id());
                put("startTime", train.getStartTime());
                put("description", train.getStations());
            }};
        }).collect(Collectors.toList());

        response.put("success", true);
        response.put("data", data);
        return response;
    }

//    private User currentUser() {
//        String currentUserUrl = USER_URL + "/current-user";
//        User user = restTemplate.getForObject(currentUserUrl, User.class);
//        System.out.println(user);
//        return user;
//    }
}
