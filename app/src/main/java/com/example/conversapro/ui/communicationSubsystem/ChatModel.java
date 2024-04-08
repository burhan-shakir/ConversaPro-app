package com.example.conversapro.ui.communicationSubsystem;

import java.util.ArrayList;
import java.util.List;
// Models all fields required when creating a chat with their getters and setters (entity class)
public class ChatModel {
    private String senderID;
    private String recvName;
    private String description;
    private String chatName;
    private List<MsgModel> messages;
    private int roomID;
    public ChatModel(String chatName, String description,  String recvName, String senderName, int roomID) {
        this.chatName = chatName;
        this.description = description;
        this.recvName = recvName;
        this.senderID = senderName;
        this.roomID = roomID;
    }

    public ChatModel(){

    }

    public String getChatName() {
        return this.chatName;
    }

    public void setChatName(String message) {
        this.chatName = message;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String senderID) {
        this.description = description;
    }

    public String getSenderID() {
        return this.senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getRecvName(){ return this.recvName; }

    public void setRecvName(String recvName) { this.recvName = recvName; }

    public int getRoomID(){return this.roomID;}
    public void setRoomID(int settingRoomID){this.roomID = settingRoomID;}

    public void addMessage(MsgModel message) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
    }
    public List<MsgModel> getMessages(){ return messages;
    }
    public void setMessages(List<MsgModel> incomingMessages){messages = incomingMessages;}
}
