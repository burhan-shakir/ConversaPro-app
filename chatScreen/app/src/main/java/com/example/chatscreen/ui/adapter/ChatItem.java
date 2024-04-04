package com.example.chatscreen.ui.adapter;

public class ChatItem {
    private String contactName;
    private String messagePreview;
    // other fields like profile image, chat id, etc.

    public ChatItem(String contactName, String messagePreview) {
        this.contactName = contactName;
        this.messagePreview = messagePreview;
    }

    // Getters
    public String getContactName() {
        return contactName;
    }

    public String getMessagePreview() {
        return messagePreview;
    }

    // Add other getters and setters
}
