package com.test.service;

import com.test.entity.Ticket;

import java.util.List;

public interface TicketService {

    Ticket getTicketByTicketId(String ticketId);

    String getStartStationByTicketId(String ticketId);

    String getTrainIdByTickedTicketId(String ticketId);

    void addTicket(String userId, String trainId, String startStation, String endStation);

    List<Ticket> getTicketsByUserId(String userId);

    void deleteTicket(int ticketId);

}
