package com.test.entity;

import lombok.Data;

@Data
public class Ticket {

    String ticketId;
    String userId;
    String trainId;
    String startStation;
    String endStation;
}
