package com.test.service.impl;

import com.test.mapper.TicketMapper;
import com.test.service.TicketService;
import org.springframework.stereotype.Service;
import com.test.entity.Ticket;


import javax.annotation.Resource;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Resource
    TicketMapper mapper;

    @Override
    public Ticket getTicketByTicketId(String ticketId) {
        return mapper.getTicketByTicketId(ticketId);
    }

    @Override
    public String getStartStationByTicketId(String ticketId) {
        return mapper.getStartStationByTicketId(ticketId);
    }

    @Override
    public String getTrainIdByTickedTicketId(String ticketId) {
        return mapper.getTrainIdByTickedTicketId(ticketId);
    }

    @Override
    public void addTicket(String userId, String trainId, String startStation, String endStation) {
        mapper.addTicket(userId, trainId, startStation, endStation);
    }

    @Override
    public void deleteTicket(String ticketId) {
        mapper.deleteTicket(ticketId);
    }

    @Override
    public List<Ticket> getTicketsByUserId(String userId) {
        return mapper.getTicketsByUserId(userId);
    }
}
