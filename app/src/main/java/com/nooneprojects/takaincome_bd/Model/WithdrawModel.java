package com.nooneprojects.takaincome_bd.Model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class WithdrawModel {

    @ServerTimestamp
    private Date createdAt;
    String userName, userPhoneNumber, uid, demand, totalBalance,paymentStatus;



    public WithdrawModel() {
    }


    public WithdrawModel(Date createdAt, String userName, String userPhoneNumber, String uid, String demand, String totalBalance, String paymentStatus) {
        this.createdAt = createdAt;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.uid = uid;
        this.demand = demand;
        this.totalBalance = totalBalance;
        this.paymentStatus = paymentStatus;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
