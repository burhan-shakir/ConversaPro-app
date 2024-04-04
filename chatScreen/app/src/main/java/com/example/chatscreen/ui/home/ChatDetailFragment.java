package com.example.chatscreen.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.chatscreen.R;

public class ChatDetailFragment extends Fragment {

    private String contactName;
    private String messagePreview;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_detail, container, false);
        TextView tvChatDetail = view.findViewById(R.id.tvChatDetail);

        if (getArguments() != null) {
            contactName = getArguments().getString("contactName");
            messagePreview = getArguments().getString("messagePreview");
            tvChatDetail.setText(contactName + ": " + messagePreview);
        }

        return view;
    }
}
