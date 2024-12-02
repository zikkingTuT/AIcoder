package com.china.powerms.service;

import com.china.powerms.entity.PaymentReminder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author admin
* @description 针对表【payment_reminder】的数据库操作Service
* @createDate 2024-11-22 23:26:33
*/
public interface PaymentReminderService extends IService<PaymentReminder> {
    PaymentReminder checkExistingReminder(Integer userId, String month);
}
