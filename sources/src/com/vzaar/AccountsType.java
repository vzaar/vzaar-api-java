package com.vzaar;

import java.math.BigInteger;

import com.google.gson.Gson;

class Rights {
    private boolean borderless;
    private boolean searchEnhancer;

    public boolean borderless() {
        return borderless;
    }

    public void borderless(boolean borderless) {
        this.borderless = borderless;
    }

    public boolean searchEnhancer() {
        return searchEnhancer;
    }

    public void searchEnhancer(boolean searchEnhancer) {
        this.searchEnhancer = searchEnhancer;
    }

    public Rights() {
    }

    public Rights(boolean borderless, boolean searchEnhancer) {
        this.borderless = borderless;
        this.searchEnhancer = searchEnhancer;
    }

    @Override
    public String toString() {
        return "Rights [borderless=" + borderless + ", searchEnhancer="
                + searchEnhancer + "]";
    }
}

class Cost {
    private String currency;
    private Integer monthly;

    public String currency() {
        return currency;
    }

    public void currency(String currency) {
        this.currency = currency;
    }

    public Integer monthly() {
        return monthly;
    }

    public void monthly(Integer monthly) {
        this.monthly = monthly;
    }

    public Cost() {
    }

    public Cost(String currency, Integer monthly) {
        this.currency = currency;
        this.monthly = monthly;
    }

    @Override
    public String toString() {
        return "Cost [currency=" + currency + ", monthly=" + monthly + "]";
    }

}

public class AccountsType {
    private BigInteger account_id;
    private BigInteger bandwidth;
    private String version;
    private String title;
    private Cost cost;
    private Rights rights;

    public AccountsType() {
    }

    public BigInteger accountId() {
        return account_id;
    }

    public void accountId(BigInteger account_id) {
        this.account_id = account_id;
    }

    public BigInteger bandwidth() {
        return bandwidth;
    }

    public void bandwidth(BigInteger bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String version() {
        return version;
    }

    public void version(String version) {
        this.version = version;
    }

    public String title() {
        return title;
    }

    public void title(String title) {
        this.title = title;
    }

    public Cost cost() {
        return cost;
    }

    public void cost(Cost cost) {
        this.cost = cost;
    }

    public Rights rights() {
        return rights;
    }

    public void rights(Rights rights) {
        this.rights = rights;
    }

    public AccountsType(String version,
                        BigInteger account_id, String title, Integer monthly, String currency,
                        BigInteger bandwidth,
                        boolean borderless, boolean searchEnhancer) {

        this.account_id = account_id;
        this.bandwidth = bandwidth;
        this.version = version;
        this.title = title;
        this.cost = new Cost(currency, monthly);
        this.rights = new Rights(borderless, searchEnhancer);
    }

    public static AccountsType fromJson(String data) {
        AccountsType accountType = null;

        Gson gson = new Gson();
        accountType = gson.fromJson(data, AccountsType.class);

        return accountType;
    }

    @Override
    public String toString() {
        return "account_id=" + account_id + ", bandwidth="
                + bandwidth + ", version=" + version + ", title=" + title
                + ", cost=" + cost.toString() + ", rights=" + rights.toString();
    }
}
