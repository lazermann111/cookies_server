package com.lazermann.cookies.dto;


public class YtCounterDto {

    public YtCounterDto() {
    }

    private int id;

    private String videoURL;
    private long counter;


    public int getId() {
        return id;
    }

    public void setId(int id) {
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }
}
