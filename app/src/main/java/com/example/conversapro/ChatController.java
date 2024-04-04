package com.example.conversapro;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class ChatController extends MainActivity implements View.OnClickListener {
    private static Context chatContext;

    public ChatController(Context context) {
        ChatController.chatContext = context;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.chat_btn) {
            Intent intent = new Intent(chatContext, ChatView.class);
            chatContext.startActivity(intent);
        }
    }
}
