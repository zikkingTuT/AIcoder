package com.china.powerms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.china.powerms.dto.ElectricityBillingGenerateDTO;
import com.china.powerms.entity.ElectricityBilling;
import com.china.powerms.service.ElectricityBillingService;
import com.china.powerms.mapper.ElectricityBillingMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author admin
 * @description 针对表【electricity_billing】的数据库操作Service实现
 * @createDate 2024-11-22 23:26:11
 */
@Service
public class ElectricityBillingServiceImpl extends ServiceImpl<ElectricityBillingMapper, ElectricityBilling>
        implements ElectricityBillingService {


    /**
     * 电费阶梯价格配置
     */
    private static final BigDecimal FIRST_LEVEL_PRICE = new BigDecimal("0.5");  // 第一档价格
    private static final BigDecimal SECOND_LEVEL_PRICE = new BigDecimal("0.8"); // 第二档价格
    private static final BigDecimal THIRD_LEVEL_PRICE = new BigDecimal("1.2");  // 第三档价格

    private static final int FIRST_LEVEL_LIMIT = 180;   // 第一档用电量上限
    private static final int SECOND_LEVEL_LIMIT = 300;  // 第二档用电量上限

    /**
     * 计算梯度电费
     *
     * @param monthlyUsage 月用电量
     * @return 应缴电费
     */
    public BigDecimal calculateGradientElectricityFee(BigDecimal monthlyUsage) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        if (monthlyUsage.compareTo(BigDecimal.ZERO) <= 0) {
            return totalAmount;
        }

        // 1. 计算第一档电费（0-180度）
        if (monthlyUsage.compareTo(new BigDecimal(FIRST_LEVEL_LIMIT)) <= 0) {
            return monthlyUsage.multiply(FIRST_LEVEL_PRICE);
        } else {
            totalAmount = totalAmount.add(new BigDecimal(FIRST_LEVEL_LIMIT).multiply(FIRST_LEVEL_PRICE));
        }

        // 2. 计算第二档电费（181-300度）
        if (monthlyUsage.compareTo(new BigDecimal(SECOND_LEVEL_LIMIT)) <= 0) {
            BigDecimal secondLevelUsage = monthlyUsage.subtract(new BigDecimal(FIRST_LEVEL_LIMIT));
            return totalAmount.add(secondLevelUsage.multiply(SECOND_LEVEL_PRICE));
        } else {
            BigDecimal secondLevelUsage = new BigDecimal(SECOND_LEVEL_LIMIT - FIRST_LEVEL_LIMIT);
            totalAmount = totalAmount.add(secondLevelUsage.multiply(SECOND_LEVEL_PRICE));
        }

        // 3. 计算第三档电费（301度以上）
        BigDecimal thirdLevelUsage = monthlyUsage.subtract(new BigDecimal(SECOND_LEVEL_LIMIT));
        totalAmount = totalAmount.add(thirdLevelUsage.multiply(THIRD_LEVEL_PRICE));

        // 保留两位小数，四舍五入
        return totalAmount.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 生成电费账单
     */
    public ElectricityBilling generateBill(ElectricityBillingGenerateDTO generateDTO) {
        ElectricityBilling billing = new ElectricityBilling();
        // 设置基本信息
        billing.setMonth(generateDTO.getMonth());
        billing.setCommunityName(generateDTO.getCommunityName());
        billing.setHouseNumber(generateDTO.getHouseNumber());
        billing.setOwnerName(generateDTO.getOwnerName());
        billing.setUserId(generateDTO.getUserId());
        billing.setMonthlyUsage(generateDTO.getMonthlyUsage());

        // 计算电费
        BigDecimal amountDue = calculateGradientElectricityFee(generateDTO.getMonthlyUsage());
        billing.setAmountDue(amountDue);

        // 设置支付状态
        billing.setPaymentStatus("UNPAID");

        // 保存账单
        this.save(billing);

        return billing;
    }


    /**
     * 批量生成一年的历史账单记录
     * @param ownerName 业主姓名
     * @return 生成的账单列表
     */
    public List<ElectricityBilling> generateYearlyBills(String ownerName) {
        List<ElectricityBilling> bills = new ArrayList<>();
        Random random = new Random();

        // 生成社区名称和房号（随机生成一次，后续复用）
        String[] communities = {"幸福小区", "阳光花园", "翠竹园", "金色家园"};
        String[] buildings = {"1栋", "2栋", "3栋", "4栋", "5栋"};
        String[] units = {"1单元", "2单元", "3单元"};
        String[] rooms = {"101", "102", "201", "202", "301", "302"};

        String communityName = communities[random.nextInt(communities.length)];
        String houseNumber = buildings[random.nextInt(buildings.length)] +
                units[random.nextInt(units.length)] +
                rooms[random.nextInt(rooms.length)];
        Integer usrid = (1000 + random.nextInt(1000)); // 随机生成用户ID

        // 获取当前月份
        LocalDate currentDate = LocalDate.now();

        // 生成12个月的账单
        for (int i = 0; i < 12; i++) {
            ElectricityBilling billing = new ElectricityBilling();

            // 计算月份，从当前月往前推
            LocalDate billDate = currentDate.minusMonths(i);
            String month = billDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));

            // 随机生成用电量 (10-500)
            BigDecimal monthlyUsage = new BigDecimal(random.nextInt(491) + 10)
                    .setScale(2, RoundingMode.HALF_UP);

            // 计算电费
            BigDecimal amountDue = calculateGradientElectricityFee(monthlyUsage);
            // 设置账单信息
            billing.setMonth(month);
            billing.setCommunityName(communityName);
            billing.setHouseNumber(houseNumber);
            billing.setOwnerName(ownerName);
            billing.setUserId(usrid);
            billing.setMonthlyUsage(monthlyUsage);
            billing.setAmountDue(amountDue);
            billing.setPaymentStatus("UNPAID"); // 历史账单默认已支付
            billing.setPaymentNumber(generatePaymentNumber(month)); // 生成支付流水号
            bills.add(billing);
        }
        // 批量保存账单
        this.saveBatch(bills);

        return bills;
    }

    /**
     * 生成支付流水号
     */
    private String generatePaymentNumber(String month) {
        return "PAY" + month.replace("-", "") +
                String.format("%04d", new Random().nextInt(10000));
    }

    @Override
    public void generate(String name) {
       generateYearlyBills(name);
    }
}




