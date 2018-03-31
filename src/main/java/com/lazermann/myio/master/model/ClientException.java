package com.lazermann.myio.master.model;

import javax.persistence.*;

@Entity
@Table(name="ClientException")
public class ClientException {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    String errorMessage;
    String methodName;
    long timestamp;
    long clientId;
    Region region;

    public ClientException(){}

    public ClientException(String errorMessage, String methodName, long timestamp, long clientId, Region region) {
        this.errorMessage = errorMessage;
        this.methodName = methodName;
        this.timestamp = timestamp;
        this.region = region;
        this.clientId = clientId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
