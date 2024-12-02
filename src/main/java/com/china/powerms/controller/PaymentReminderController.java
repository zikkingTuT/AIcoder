package com.china.powerms.controller;

import com.china.powerms.dto.PaymentReminderDTO;
import com.china.powerms.entity.PaymentReminder;
import com.china.powerms.service.PaymentReminderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/payment-reminder")
@Slf4j
public class PaymentReminderController {

    @Resource
    private PaymentReminderService paymentReminderService;

    /**
     * 创建缴费提醒记录
     */
    @PostMapping("/create")
    public ResponseEntity<Object> createPaymentReminder(@RequestBody @Validated PaymentReminderDTO dto) {
        try {
            // 先检查是否存在相同用户同月份的提醒
            PaymentReminder existingReminder = paymentReminderService.checkExistingReminder(
                    dto.getUserId(),
                    dto.getMonth()
            );
            if (existingReminder != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("该月份的缴费提示已送达，请勿重复提示");
            }
            // 创建新的提醒记录
            PaymentReminder reminder = new PaymentReminder();
            reminder.setUserId(dto.getUserId());
            reminder.setMonth(dto.getMonth());
            reminder.setMonthlyUsage(dto.getMonthlyUsage());
            reminder.setAmountDue(dto.getAmountDue());
            reminder.setPaymentStatus("UNPAID");
            boolean success = paymentReminderService.save(reminder);
            if (success) {
                return ResponseEntity.ok(reminder);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("创建缴费提醒失败");
            }
        } catch (Exception e) {
            log.error("创建缴费提醒异常", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("系统异常：" + e.getMessage());
        }
    }
}
