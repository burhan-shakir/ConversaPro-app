package com.example.conversapro;

import static com.example.conversapro.ChatView.reciverIImg;
import static com.example.conversapro.ChatView.senderImg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MsgsAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<MsgModel> messagesAdapterArrayList;
    int ITEM_SEND=1;
    int ITEM_RECEIVE=2;
    public MsgsAdapter(Context context, ArrayList<MsgModel> messagesAdapterArrayList){
        this.context = context;
        this.messagesAdapterArrayList = messagesAdapterArrayList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SEND){
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.reciver_layout, parent, false);
            return new RecvrViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MsgModel messages = messagesAdapterArrayList.get(position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context).setTitle("Delete")
                        .setMessage("Are you sure you want to delete this message?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

                return false;
            }
        });
        if (holder.getClass() == SenderViewHolder.class){
            SenderViewHolder senderViewHolder = (SenderViewHolder) holder;
            senderViewHolder.msgtxt.setText(messages.getMessage());
            //Picasso.get().load(senderImg).into(senderViewHolder.circleImageView);
        } else { RecvrViewHolder viewHolder = (RecvrViewHolder) holder;
            viewHolder.msgtxt.setText(messages.getMessage());
            //Picasso.get().load(reciverIImg).into(viewHolder.circleImageView);


        }
    }

    @Override
    public int getItemCount() {
        return this.messagesAdapterArrayList.size();
    }

    class SenderViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView msgtxt;
        public SenderViewHolder(@NonNull View view){
            super(view);
            circleImageView = itemView.findViewById(R.id.profilerggg);
            msgtxt = itemView.findViewById(R.id.msgsendertyp);
        }
    }

    class RecvrViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView msgtxt;
        public RecvrViewHolder(@NonNull View view){
            super(view);
            circleImageView = itemView.findViewById(R.id.pro);
            msgtxt = itemView.findViewById(R.id.receivertextset);
        }
    }
}
