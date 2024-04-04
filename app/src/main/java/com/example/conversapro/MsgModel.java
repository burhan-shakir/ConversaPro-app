package com.example.conversapro;

import java.util.Date;

public class MsgModel {
    private String message;
    private String senderID;
    private long timeStamp;
    public MsgModel(String message, String senderID) {
        this.message = message;
        this.senderID = senderID;
        timeStamp = new Date().getTime();
    }

    public MsgModel(){

    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderID() {
        return this.senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

}
