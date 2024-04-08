package com.example.conversapro.ui.chatScreen;

import java.util.Date;
// Models all fields required in a message with their getters and setters (entity class)
public class MsgModel {
    private String message;
    private String senderID;
    private String receiver;
    private long timeStamp;
    public MsgModel(String message, String senderID, String receiver) {
        this.message = message;
        this.senderID = senderID;
        this.receiver = receiver;
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

    public String getReceiver() { return this.receiver;}

    public void setReceiver(String receiver){ this.receiver = receiver; }


}