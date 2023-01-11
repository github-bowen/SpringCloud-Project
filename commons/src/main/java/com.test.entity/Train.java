package com.test.entity;

import lombok.Data;

@Data
public class Train {
    String tid;
    String stations;
    int capacity;
    String startTime;
    String frequency;
    int remain;
}
