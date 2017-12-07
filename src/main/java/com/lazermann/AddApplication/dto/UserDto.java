package com.lazermann.AddApplication.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class UserDto {
    public UserDto() { }

    public UserDto(long card_id) {
        this.id = card_id;
    }

    public UserDto(String firstName, String secondName, String email, float balance) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.balance = balance;
    }

    private long id;

    private  String firstName;
    private  String secondName;
    private  String email;
    private  float balance;

    public long getId() {
        return id;
    }

    public void setId(long card_id) {
        this.id = card_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
