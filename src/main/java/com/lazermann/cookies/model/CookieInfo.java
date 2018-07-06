package com.lazermann.cookies.model;


import javax.persistence.*;

@Entity
@Table(name="cookies")
public class CookieInfo
{
    public CookieInfo() { }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String proxy;
    private String cookie;


    public String getProxy() {
        return proxy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
