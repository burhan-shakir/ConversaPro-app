package com.example.conversapro.ui.communicationSubsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.conversapro.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
// Arranges all the messages in the desired layout and populates them (Controller class)
public class ChatAdapter extends ArrayAdapter<MsgModel> {
    public ChatAdapter(Context context, List<MsgModel> messages){

        super(context, 0, messages);
    }
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.message, parent, false);
        }
        // Set all message fields on screen
        MsgModel currentMessage = getItem(position);

        TextView textViewSender = listItemView.findViewById(R.id.textViewSender);
        textViewSender.setText("From: " + currentMessage.getSenderID());

        TextView textViewReceiver = listItemView.findViewById(R.id.textViewReceiver);
        textViewReceiver.setText("To: " + currentMessage.getReceiver());

        TextView textViewMessage = listItemView.findViewById(R.id.textViewMessage);
        textViewMessage.setText(currentMessage.getMessage());

        TextView textViewTimestamp = listItemView.findViewById(R.id.textViewTime);
        String formattedDate = formatDate(currentMessage.getTimeStamp());
        textViewTimestamp.setText(formattedDate);

        return listItemView;
    }

    private String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
}
