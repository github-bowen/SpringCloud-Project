package com.test.entity;

import lombok.Data;

@Data
public class Train {
    String tid;
    String stations;
    int capacity;
    String startTime;
    String frequency;
    // 该属性在数据库中要求非负数 (`remain` int unsigned NOT NULL)
    int remain;
}
