package com.vzaar;

import com.google.gson.Gson;

public class AccountDetails {
    public String version;
    public int accountId;
    public long bandwidth;
    public String title;
    public AccountRightsDetails rights;
    public AccountCostDetails cost;

    public static AccountDetails fromJson(String json) {
        AccountDetails accountDetails = new AccountDetails();
        Gson gson = new Gson();
        accountDetails = gson.fromJson(json, AccountDetails.class);
        return accountDetails;
    }

    @Override
    public String toString() {
        return "AccountDetails{" +
                "version='" + version + '\'' +
                ", accountId=" + accountId +
                ", bandwidth=" + bandwidth +
                ", title='" + title + '\'' +
                ", rights=" + rights +
                ", cost=" + cost +
                '}';
    }
}
