package com.lazermann.AddApplication.model;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name="purchases")
public class Purchase {
    public Purchase() {
    }

    public Purchase(String card_id, float price) {
        this.badgeId = card_id;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String employeeId;
    private String badgeId;
    private float price;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getBadgeId() {
        return badgeId;
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
