package com.lazermann.AddApplication.dto;

import java.util.Calendar;

public class PurchaseDto {
    public PurchaseDto() {
    }

    public PurchaseDto(String card_id, float price) {
        this.badgeId = card_id;
        this.price = price;
    }

    private long id;
    private String employeeId;
    private String badgeId;
    private float price;
    private Calendar date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBadgeId() {
        return badgeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setBadgeId(String badgeId) {
        this.badgeId = badgeId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
