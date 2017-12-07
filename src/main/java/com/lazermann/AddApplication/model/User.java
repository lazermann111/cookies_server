package com.lazermann.AddApplication.model;


import javax.persistence.*;

@Entity
@Table(name="users")
public class User
{
    public User() { }

    public User(long card_id) {
        this.id = card_id;
    }


    public User(String firstName, String secondName, String email, float balance) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.balance = balance;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private  String firstName;
    private  String secondName;
    private  String email;
    private  float balance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
