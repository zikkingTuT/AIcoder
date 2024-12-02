package com.china.powerms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.china.powerms.entity.PaymentReminder;
import com.china.powerms.service.PaymentReminderService;
import com.china.powerms.mapper.PaymentReminderMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【payment_reminder】的数据库操作Service实现
* @createDate 2024-11-22 23:26:33
*/
@Service
public class PaymentReminderServiceImpl extends ServiceImpl<PaymentReminderMapper, PaymentReminder>
    implements PaymentReminderService{
    /**
     * 检查是否存在相同用户同月份的提醒
     * @param userId 用户ID
     * @param month 月份
     * @return 已存在的提醒记录，不存在则返回null
     */
    public PaymentReminder checkExistingReminder(Integer userId, String month) {
        LambdaQueryWrapper<PaymentReminder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PaymentReminder::getUserId, userId)
                .eq(PaymentReminder::getMonth, month);

        return this.getOne(queryWrapper);
    }
}




