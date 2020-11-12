package com.codetrex.cayroshop.ui;

import com.codetrex.cayroshop.model.UserWalletTransaction;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserWalletModel {
    @SerializedName("UserWalletTransaction")
    @Expose
    private List<UserWalletTransaction> userWalletTransaction = null;

    public List<UserWalletTransaction> getUserWalletTransaction() {
        return userWalletTransaction;
    }

    public void setUserWalletTransaction(List<UserWalletTransaction> userWalletTransaction) {
        this.userWalletTransaction = userWalletTransaction;
    }
}
