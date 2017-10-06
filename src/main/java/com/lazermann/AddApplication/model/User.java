package com.lazermann.AddApplication.model;


import javax.persistence.*;

@Entity
@Table(name="users")
public class User
{
    public User() { }

    public User(long id) {
        this.id = id;
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private  String username;
    private  String email;
    private  String password;

    private int pointsTotal;
    private int pointsToday;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPointsTotal() {
        return pointsTotal;
    }

    public void setPointsTotal(int pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    public int getPointsToday() {
        return pointsToday;
    }

    public void setPointsToday(int pointsToday) {
        this.pointsToday = pointsToday;
    }
}
