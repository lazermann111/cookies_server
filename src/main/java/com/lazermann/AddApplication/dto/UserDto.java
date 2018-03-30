package com.lazermann.AddApplication.dto;

public class UserDto {
    public UserDto() { }

    public UserDto(long card_id) {
        this.id = card_id;
    }

    public UserDto(String username) {
        this.username = username;
    }


    private long id;

    private String username;
    private boolean isActive;

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }


}
