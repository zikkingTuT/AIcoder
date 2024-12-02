package com.china.powerms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName payment_reminder
 */
@TableName(value ="payment_reminder")
@Data
public class PaymentReminder implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private String month;

    /**
     * 
     */
    private BigDecimal monthlyUsage;

    /**
     * 
     */
    private BigDecimal amountDue;

    /**
     * 
     */
    private String paymentStatus;

    /**
     * 
     */
    private Date reminderDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PaymentReminder other = (PaymentReminder) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getMonth() == null ? other.getMonth() == null : this.getMonth().equals(other.getMonth()))
            && (this.getMonthlyUsage() == null ? other.getMonthlyUsage() == null : this.getMonthlyUsage().equals(other.getMonthlyUsage()))
            && (this.getAmountDue() == null ? other.getAmountDue() == null : this.getAmountDue().equals(other.getAmountDue()))
            && (this.getPaymentStatus() == null ? other.getPaymentStatus() == null : this.getPaymentStatus().equals(other.getPaymentStatus()))
            && (this.getReminderDate() == null ? other.getReminderDate() == null : this.getReminderDate().equals(other.getReminderDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getMonth() == null) ? 0 : getMonth().hashCode());
        result = prime * result + ((getMonthlyUsage() == null) ? 0 : getMonthlyUsage().hashCode());
        result = prime * result + ((getAmountDue() == null) ? 0 : getAmountDue().hashCode());
        result = prime * result + ((getPaymentStatus() == null) ? 0 : getPaymentStatus().hashCode());
        result = prime * result + ((getReminderDate() == null) ? 0 : getReminderDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", month=").append(month);
        sb.append(", monthlyUsage=").append(monthlyUsage);
        sb.append(", amountDue=").append(amountDue);
        sb.append(", paymentStatus=").append(paymentStatus);
        sb.append(", reminderDate=").append(reminderDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}