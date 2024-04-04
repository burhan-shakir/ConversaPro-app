package com.example.conversapro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatView extends AppCompatActivity {
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_page);
        display_chat_messages();
        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);
                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                database.getInstance()
                        .getReference()
                        .push()
                        .setValue(new MsgModel(input.getText().toString(), "Burhan Shakir")
                        );
                // Clear the input
                input.setText("");
            }

        });
    }
    private void display_chat_messages() {
       //DatabaseReference reference = database.getInstance().getReference();
        ListView listOfMessages = (ListView) findViewById(R.id.list_of_messages);

        listOfMessages.setAdapter(new ChatAdapter());
    }
    private class ChatAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final MsgModel[] model = new MsgModel[1];
            DatabaseReference reference = database.getInstance().getReference();
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    model[0] =  snapshot.getValue(MsgModel.class);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            TextView messageText = (TextView)convertView.findViewById(R.id.message_text);
            TextView messageUser = (TextView)convertView.findViewById(R.id.message_user);
            TextView messageTime = (TextView)convertView.findViewById(R.id.message_time);
            // Set their text
            messageText.setText(model[0].getMessage());
            messageUser.setText(model[0].getSenderID());
            // Format the date before showing it
            messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                    model[0].getTimeStamp()));
            return null;
        }
    }
}