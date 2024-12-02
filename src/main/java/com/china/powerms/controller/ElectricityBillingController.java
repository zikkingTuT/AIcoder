package com.china.powerms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.china.powerms.common.R;
import com.china.powerms.dto.ElectricityBillingQueryDTO;
import com.china.powerms.dto.ElectricityBillingUpdateDTO;
import com.china.powerms.entity.ElectricityBilling;
import com.china.powerms.entity.PaymentReminder;
import com.china.powerms.service.ElectricityBillingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/electricity-billing")
public class ElectricityBillingController {

    @Resource
    private ElectricityBillingService electricityBillingService;

    /**
     * 动态条件查询
     */
    @PostMapping("/list")
    public R<IPage<ElectricityBilling>> list(@RequestBody ElectricityBillingQueryDTO queryDTO) {
        // 创建分页对象
        Page<ElectricityBilling> page = new Page<>(queryDTO.getPage(), queryDTO.getPageSize());

        LambdaQueryWrapper<ElectricityBilling> queryWrapper = new LambdaQueryWrapper<>();
        // 动态添加查询条件
        queryWrapper.eq(StringUtils.isNotBlank(queryDTO.getMonth()),
                        ElectricityBilling::getMonth, queryDTO.getMonth())
                .eq(StringUtils.isNotBlank(queryDTO.getPaymentStatus()),
                        ElectricityBilling::getPaymentStatus, queryDTO.getPaymentStatus())
                .eq(StringUtils.isNotBlank(queryDTO.getPaymentNumber()),
                        ElectricityBilling::getPaymentNumber, queryDTO.getPaymentNumber())
                .eq(StringUtils.isNotBlank(queryDTO.getName()),
                        ElectricityBilling::getOwnerName, queryDTO.getName());

        // 执行分页查询
        IPage<ElectricityBilling> pageResult = electricityBillingService.page(page, queryWrapper);
        return R.ok(pageResult);
    }

    /**
     * 根据业主姓名修改支付状态
     */
    @PostMapping("/update-status")
    public R<?> updatePaymentStatus(@RequestBody ElectricityBillingUpdateDTO updateDTO) {
        LambdaUpdateWrapper<ElectricityBilling> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ElectricityBilling::getOwnerName, updateDTO.getOwnerName())
                .set(ElectricityBilling::getPaymentStatus, updateDTO.getPaymentStatus());
        boolean success = electricityBillingService.update(updateWrapper);
        LambdaUpdateWrapper<PaymentReminder> PaymentReminderupdateWrapper = new LambdaUpdateWrapper<>();
        PaymentReminderupdateWrapper.eq(PaymentReminder::getUserId, updateDTO.getOwnerName())
                .set(PaymentReminder::getPaymentStatus, updateDTO.getPaymentStatus());
        electricityBillingService.update(updateWrapper);
        return success ? R.ok() : R.failed("更新失败");
    }


}
