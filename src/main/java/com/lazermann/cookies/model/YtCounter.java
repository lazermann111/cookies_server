package com.lazermann.cookies.model;

import javax.persistence.*;


@Entity
@Table(name="ytcounters")
public class YtCounter
{

    public YtCounter(){}


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String videoURL;
    private long counter;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
