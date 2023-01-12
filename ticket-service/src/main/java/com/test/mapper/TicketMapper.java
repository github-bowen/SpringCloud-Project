package com.test.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.test.entity.Ticket;

import java.util.List;

@Mapper
public interface TicketMapper {

    @Select("select * from db_ticket where ticket_id = #{ticketId}")
    Ticket getTicketByTicketId(String ticketId);

    @Select("select startStation from db_ticket where ticket_id = #{ticketId}")
    String getStartStationByTicketId(String ticketId);

    @Select("select trainId from db_ticket where ticket_id = #{ticketId}")
    String getTrainIdByTickedTicketId(String ticketId);

    @Delete("delete from db_ticket where ticket_id = #{ticket_id}")
    void deleteTicket(int ticket_id);

    @Insert("insert into db_ticket (user_id, train_id, startStation, endStation) " +
            "values (#{userId}, #{trainId}, #{startStation}, #{endStation});")
    void addTicket(String userId, String trainId, String startStation, String endStation);

    @Delete("delete from db_ticket where ticket_id = #{ticketId}")
    void deleteTicket(String ticketId);


    @Select("select * from db_ticket where user_id = #{userId}")
    List<Ticket> getTicketsByUserId(String userId);

}
