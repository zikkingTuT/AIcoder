package com.china.powerms.dto;

import lombok.Data;

@Data
public class ElectricityBillingQueryDTO {
    private String month;
    private String paymentStatus;
    private String paymentNumber;
    private String name;
    private Integer page = 1;    // 当前页码
    private Integer pageSize = 10;  // 每页显示条数
}
