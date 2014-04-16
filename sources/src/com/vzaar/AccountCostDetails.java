package com.vzaar;

public class AccountCostDetails {
    public String currency;
    public int monthly;

    @Override
    public String toString() {
        return "AccountCostDetails{" +
                "currency='" + currency + '\'' +
                ", monthly=" + monthly +
                '}';
    }
}
