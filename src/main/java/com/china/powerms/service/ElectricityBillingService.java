package com.china.powerms.service;

import com.china.powerms.entity.ElectricityBilling;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
* @author admin
* @description 针对表【electricity_billing】的数据库操作Service
* @createDate 2024-11-22 23:26:11
*/
public interface ElectricityBillingService extends IService<ElectricityBilling> {
        void generate(String name);

        BigDecimal calculateGradientElectricityFee (BigDecimal fee);
}
