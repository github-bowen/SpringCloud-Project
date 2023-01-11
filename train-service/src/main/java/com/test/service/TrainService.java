package com.test.service;

import com.test.entity.Train;

import java.util.List;

public interface TrainService {

    Train getTrainById(String tid);

    List<Train> getAllTrains();

    // 增加后，要吧火车剩余票数 remain 初始化成 capacity
    void addTrain(String tid, String stations,
                  int capacity, String startTime,
                  String frequency);

    // FIXME: 这里与 "前端接口文档" 不同，只需要火车id即可
    void deleteTrain(String tid);

    void modifyTrain(String tid, int capacity, String startTime, String frequency);
}
