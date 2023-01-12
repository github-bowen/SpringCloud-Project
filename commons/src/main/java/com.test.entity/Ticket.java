package com.test.entity;

import lombok.Data;

@Data
public class Ticket {

    String ticket_id;
    String user_id;
    String train_id;
    String startStation;
    String endStation;
}
