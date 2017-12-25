package com.lazermann.AddApplication.model;

import javax.persistence.*;

@Entity
@Table(name="passwords")
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String password;

    public Password() {
    }

    public Password(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
