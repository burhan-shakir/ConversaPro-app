package com.example.conversapro.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conversapro.R;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {
    private List<ChatItem> chatList;
    private int selectedPosition = RecyclerView.NO_POSITION;

    private final OnChatItemClickListener onChatItemClickListener;

    public ChatListAdapter(List<ChatItem> chatList, OnChatItemClickListener listener) {
        this.chatList = chatList;
        this.onChatItemClickListener = listener;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view, onChatItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        final ChatItem chatItem = chatList.get(position);

        // Set background color based on selected state
        holder.itemView.setBackgroundColor(selectedPosition == position ? Color.LTGRAY : Color.TRANSPARENT);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Updating old as well as new positions
                notifyItemChanged(selectedPosition);
                selectedPosition = holder.getLayoutPosition();
                notifyItemChanged(selectedPosition);

                // Handle the click event for item
                onChatItemClickListener.onChatClick(chatItem);
            }
        });

    }
    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView contactName;
        TextView messagePreview;

        public ChatViewHolder(View itemView, OnChatItemClickListener onChatItemClickListener) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contact_name);
            messagePreview = itemView.findViewById(R.id.message_preview);
        }
    }
    // Interface for click events was moved to separate interface file
}