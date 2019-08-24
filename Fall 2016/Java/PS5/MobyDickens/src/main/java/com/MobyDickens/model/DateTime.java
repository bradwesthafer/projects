package com.MobyDickens.model;

/**
 * Created by Brad on 11/20/2016.
 */
public class DateTime {
    private String month;
    private int year;

    public DateTime(String month, int year) {
        this.month = month;
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
