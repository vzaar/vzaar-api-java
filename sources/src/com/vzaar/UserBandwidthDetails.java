package com.vzaar;

public class UserBandwidthDetails {
    public int month;
    public int year;
    public long bandwidth;

    @Override
    public String toString() {
        return "UserBandwidthDetails{" +
                "month=" + month +
                ", year=" + year +
                ", bandwidth=" + bandwidth +
                '}';
    }
}
