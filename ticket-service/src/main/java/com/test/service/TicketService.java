package com.test.service;

import sun.security.krb5.internal.Ticket;

public interface TicketService {

    Ticket getTicketByTicketId(String ticketId);

    String getStartStationByTicketId(String ticketId);

    String getTrainByTickedTicketId(String ticketId);

    String purchaseTicket(String ticketId);

}
