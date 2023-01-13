package com.test.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.test.entity.Train;
import com.test.mapper.TrainMapper;
import com.test.service.TrainService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainServiceImpl implements TrainService {

    @Resource
    TrainMapper trainMapper;

    @Override
    public Train getTrainById(String tid) {
        return trainMapper.getTrainById(tid);
    }

    // 获取全部车次信息，信息较多，当请求达到一定数量时需要进行流量控制
    @Override
    // @SentinelResource(value = "getAllTrains", fallback = "handler")
    public List<Train> getAllTrains() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }

        return trainMapper.getAllTrains();
    }

    // 替代方案
    public List<Train> handler(Throwable t) {
        System.err.println("\n执行 TrainServiceImpl::getAllTrains 替代方案！\n");
        // 返回空
        return new ArrayList<Train>();
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

    // 尝试将 tid 对应的车次的剩余票数减一，如果剩余车票为负数抛出异常
    @Override
    public void trySellingTicket(String tid) {
        trainMapper.trySellingTicket(tid);
    }

    // 退票，remain + 1
    @Override
    public void returnTicket(String tid) {
        trainMapper.returnTicket(tid);
    }
}
