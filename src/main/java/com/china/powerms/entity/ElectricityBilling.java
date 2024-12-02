package com.china.powerms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 
 * @TableName electricity_billing
 */
@TableName(value ="electricity_billing")
@Data
public class ElectricityBilling implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String month;

    /**
     * 
     */
    private String communityName;

    /**
     * 
     */
    private String houseNumber;

    /**
     * 
     */
    private String ownerName;

    /**
     * 
     */
    private Integer userId;

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
    private String paymentNumber;

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
        ElectricityBilling other = (ElectricityBilling) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMonth() == null ? other.getMonth() == null : this.getMonth().equals(other.getMonth()))
            && (this.getCommunityName() == null ? other.getCommunityName() == null : this.getCommunityName().equals(other.getCommunityName()))
            && (this.getHouseNumber() == null ? other.getHouseNumber() == null : this.getHouseNumber().equals(other.getHouseNumber()))
            && (this.getOwnerName() == null ? other.getOwnerName() == null : this.getOwnerName().equals(other.getOwnerName()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getMonthlyUsage() == null ? other.getMonthlyUsage() == null : this.getMonthlyUsage().equals(other.getMonthlyUsage()))
            && (this.getAmountDue() == null ? other.getAmountDue() == null : this.getAmountDue().equals(other.getAmountDue()))
            && (this.getPaymentStatus() == null ? other.getPaymentStatus() == null : this.getPaymentStatus().equals(other.getPaymentStatus()))
            && (this.getPaymentNumber() == null ? other.getPaymentNumber() == null : this.getPaymentNumber().equals(other.getPaymentNumber()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMonth() == null) ? 0 : getMonth().hashCode());
        result = prime * result + ((getCommunityName() == null) ? 0 : getCommunityName().hashCode());
        result = prime * result + ((getHouseNumber() == null) ? 0 : getHouseNumber().hashCode());
        result = prime * result + ((getOwnerName() == null) ? 0 : getOwnerName().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getMonthlyUsage() == null) ? 0 : getMonthlyUsage().hashCode());
        result = prime * result + ((getAmountDue() == null) ? 0 : getAmountDue().hashCode());
        result = prime * result + ((getPaymentStatus() == null) ? 0 : getPaymentStatus().hashCode());
        result = prime * result + ((getPaymentNumber() == null) ? 0 : getPaymentNumber().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", month=").append(month);
        sb.append(", communityName=").append(communityName);
        sb.append(", houseNumber=").append(houseNumber);
        sb.append(", ownerName=").append(ownerName);
        sb.append(", userId=").append(userId);
        sb.append(", monthlyUsage=").append(monthlyUsage);
        sb.append(", amountDue=").append(amountDue);
        sb.append(", paymentStatus=").append(paymentStatus);
        sb.append(", paymentNumber=").append(paymentNumber);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}