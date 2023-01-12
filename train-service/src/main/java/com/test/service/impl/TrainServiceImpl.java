package com.test.service.impl;

import com.test.entity.Train;
import com.test.mapper.TrainMapper;
import com.test.service.TrainService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TrainServiceImpl implements TrainService {

    @Resource
    TrainMapper trainMapper;

    @Override
    public Train getTrainById(String tid) {
        return trainMapper.getTrainById(tid);
    }

    @Override
    public List<Train> getAllTrains() {
        return trainMapper.getAllTrains();
    }

    @Override
    public void addTrain(String tid, String stations, int capacity,
                         String startTime, String frequency) {
        trainMapper.addTrain(tid, stations, capacity, startTime, frequency);
    }

    @Override
    public void deleteTrain(String tid) {
        trainMapper.deleteTrain(tid);
    }

    @Override
    public void modifyTrain(String tid, int capacity, String startTime, String frequency) {
        trainMapper.modifyTrain(tid, capacity, startTime, frequency);
    }
}
