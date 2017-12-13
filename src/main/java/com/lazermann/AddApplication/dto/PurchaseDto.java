package com.lazermann.AddApplication.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class PurchaseDto {
    public PurchaseDto() {
    }

    public PurchaseDto(long card_id, float price) {
        this.card_id = card_id;
        this.price = price;
    }

    private long id;
    private long card_id;
    private float price;
    private Calendar date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCard_id() {
        return card_id;
    }

    public void setCard_id(long card_id) {
        this.card_id = card_id;
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
