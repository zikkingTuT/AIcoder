package com.china.powerms.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ElectricityBillingGenerateDTO {
    private String month;
    private String communityName;
    private String houseNumber;
    private String ownerName;
    private Integer userId;
    private BigDecimal monthlyUsage;
}
