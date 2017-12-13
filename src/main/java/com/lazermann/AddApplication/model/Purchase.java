package com.lazermann.AddApplication.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="purchases")
public class Purchase {
    public Purchase() {
    }

    public Purchase(long card_id, float price) {
        this.card_id = card_id;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long card_id;
    private float price;
    @Temporal(TemporalType.TIMESTAMP)
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
