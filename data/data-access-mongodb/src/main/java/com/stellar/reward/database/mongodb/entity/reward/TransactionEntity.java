package com.stellar.reward.database.mongodb.entity.reward;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stellar.reward.database.model.CustomerType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "transaction")
public class TransactionEntity {

    @Id
    private String _id;


    @JsonProperty("customerType")
    private CustomerType customerType;

    @JsonProperty("customerName")
    private String customerName;

    @JsonProperty("phoneNumber")
    private String phoneNumber;


    @JsonProperty("totalAmountOfPurchase")
    private Long TotalAmountOfPurchase;

    @JsonProperty("transactionTime")
    private Long transactionTime;


    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getTotalAmountOfPurchase() {
        return TotalAmountOfPurchase;
    }

    public void setTotalAmountOfPurchase(Long totalAmountOfPurchase) {
        TotalAmountOfPurchase = totalAmountOfPurchase;
    }

    public Long getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Long transactionTime) {
        this.transactionTime = transactionTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionEntity that = (TransactionEntity) o;

        if (_id != null ? !_id.equals(that._id) : that._id != null) return false;
        if (getCustomerType() != null ? !getCustomerType().equals(that.getCustomerType()) : that.getCustomerType() != null)
            return false;
        if (getCustomerName() != null ? !getCustomerName().equals(that.getCustomerName()) : that.getCustomerName() != null)
            return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(that.getPhoneNumber()) : that.getPhoneNumber() != null)
            return false;
        if (getTotalAmountOfPurchase() != null ? !getTotalAmountOfPurchase().equals(that.getTotalAmountOfPurchase()) : that.getTotalAmountOfPurchase() != null)
            return false;
        return getTransactionTime() != null ? getTransactionTime().equals(that.getTransactionTime()) : that.getTransactionTime() == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (getCustomerType() != null ? getCustomerType().hashCode() : 0);
        result = 31 * result + (getCustomerName() != null ? getCustomerName().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getTotalAmountOfPurchase() != null ? getTotalAmountOfPurchase().hashCode() : 0);
        result = 31 * result + (getTransactionTime() != null ? getTransactionTime().hashCode() : 0);
        return result;
    }


}
