package com.china.powerms.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PaymentReminderDTO {

    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    @NotBlank(message = "月份不能为空")
    private String month;

    @NotNull(message = "月度用量不能为空")
    @DecimalMin(value = "0.0", message = "月度用量必须大于0")
    private BigDecimal monthlyUsage;

    @NotNull(message = "应付金额不能为空")
    @DecimalMin(value = "0.0", message = "应付金额必须大于0")
    private BigDecimal amountDue;
}
