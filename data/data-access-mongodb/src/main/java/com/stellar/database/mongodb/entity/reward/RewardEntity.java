package com.stellar.database.mongodb.entity.reward;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "reward")
public class RewardEntity {

    @Id
    private String _id;

    @JsonProperty("customerType")
    private com.stellar.reward.api.model.CustomerType customerType;

    @JsonProperty("rewardPoint")
    private Long rewardPoint;

    @JsonProperty("name")
    private String customerName;

    @JsonProperty("sumeOfRewardPoint")
    private String sumeOfRewardPoint;


    public String getCustomerName() {
        return customerName;
    }


    public String get_id() {
        return _id;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public com.stellar.reward.api.model.CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(com.stellar.reward.api.model.CustomerType customerType) {
        this.customerType = customerType;
    }

    public Long getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(Long rewardPoint) {
        this.rewardPoint = rewardPoint;
    }


    public String getSumeOfRewardPoint() {
        return sumeOfRewardPoint;
    }

    public void setSumeOfRewardPoint(String sumeOfRewardPoint) {
        this.sumeOfRewardPoint = sumeOfRewardPoint;
    }


    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        RewardEntity that = (RewardEntity) object;

        if (_id != null ? !_id.equals(that._id) : that._id != null) return false;
        if (getCustomerType() != null ? !getCustomerType().equals(that.getCustomerType()) : that.getCustomerType() != null)
            return false;
        if (getRewardPoint() != null ? !getRewardPoint().equals(that.getRewardPoint()) : that.getRewardPoint() != null)
            return false;
        if (getCustomerName() != null ? !getCustomerName().equals(that.getCustomerName()) : that.getCustomerName() != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (_id != null ? _id.hashCode() : 0);
        result = 31 * result + (getCustomerType() != null ? getCustomerType().hashCode() : 0);
        result = 31 * result + (getRewardPoint() != null ? getRewardPoint().hashCode() : 0);
        result = 31 * result + (getCustomerName() != null ? getCustomerName().hashCode() : 0);
        return result;
    }

}
