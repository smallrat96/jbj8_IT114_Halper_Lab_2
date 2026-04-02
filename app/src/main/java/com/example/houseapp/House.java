package com.example.houseapp;

import java.util.Date;

public class House {
    private String address;
    private String city;
    private String lotnum;
    private float price;
    private float ptax;
    private float sqfoot;

    private int yearBuilt;


    public float getTaxExclusion(float proptax){
        if (proptax > 10000) {
            return proptax - 10000;
        }
        else {
            return 0;
        }

    }
    public int getAge() {
        return DateUtil.get_current_year() - yearBuilt;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLotnum() {
        return lotnum;
    }

    public void setLotnum(String lotnum) {
        this.lotnum = lotnum;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPtax() {
        return ptax;
    }

    public void setPtax(float ptax) {
        this.ptax = ptax;
    }

    public float getSqfoot() {
        return sqfoot;
    }

    public void setSqfoot(float sqfoot) {
        this.sqfoot = sqfoot;
    }


    public int getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(int yearBuilt) {
        this.yearBuilt = yearBuilt;
    }
}

