package com.example.conversapro.ui.chatScreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewChatViewModel extends ViewModel {
    private MutableLiveData<String> chatName = new MutableLiveData<>();
    private MutableLiveData<String> roomID = new MutableLiveData<>();
    private MutableLiveData<String> recvName = new MutableLiveData<>();
    private String isNewChat;

    public void setChatName(String value) {
        chatName.setValue(value);
    }

    public LiveData<String> getChatName() {
        return chatName;
    }
    public void setRoomID(String value) {
        roomID.setValue(value);
    }

    public LiveData<String> getRoomID() {
        return roomID;
    }
    public void setRecvName(String value) {
        recvName.setValue(value);
    }

    public LiveData<String> getRecvName() {
        return recvName;
    }
    public void setIsNewChat(String value) {
        isNewChat = value;
    }

    public String getIsNewChat() {
        return isNewChat;
    }
}
