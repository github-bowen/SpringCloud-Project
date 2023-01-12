package com.test.mapper;

import com.test.entity.Train;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TrainMapper {

    @Select("select * from db_train where tid = #{tid}")
    Train getTrainById(String tid);

    @Select("select * from db_train")
    List<Train> getAllTrains();

    @Insert("insert into db_train values (#{tid}, #{stations}, " +
            "#{capacity}, #{startTime}, #{frequency}, #{capacity})")
    void addTrain(String tid, String stations,
                  int capacity, String startTime,
                  String frequency);


    @Delete("delete from db_train where tid = #{tid}")
    void deleteTrain(String tid);

    @Update("update db_train set capacity = #{capacity}, " +
            "startTime = #{startTime}, frequency = #{frequency} " +
            "where tid = #{tid}")
    void modifyTrain(String tid, int capacity, String startTime, String frequency);

}
