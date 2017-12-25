package com.lazermann.AddApplication.dto;

import java.util.Calendar;

public class FullPurchaseDto {
    private long id;
    private String badgeId;
    private String firstName;
    private String lastName;
    private String middleName;
    private float price;
    private Calendar date;

    public FullPurchaseDto(long id, String badgeId, String firstName, String lastName, String middleName, float price, Calendar date) {
        this.id = id;
        this.badgeId = badgeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.price = price;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(String badgeId) {
        this.badgeId = badgeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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
