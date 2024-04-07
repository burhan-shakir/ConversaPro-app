package com.example.conversapro.ui.home;

import static androidx.navigation.Navigation.findNavController;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conversapro.R;
import com.example.conversapro.databinding.FragmentHomeBinding;
import com.example.conversapro.ui.adapter.ChatListAdapter;
import com.example.conversapro.ui.adapter.ChatItem;
import com.example.conversapro.ui.adapter.OnChatItemClickListener;
import com.example.conversapro.ui.chatScreen.ChatModel;
import com.example.conversapro.ui.chatScreen.ChatScreenFragment;
import com.example.conversapro.ui.chatScreen.NewChatViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnChatItemClickListener {

    private FragmentHomeBinding binding;
    private NavController navController;
    private RecyclerView recyclerView;
    private ChatListAdapter adapter;
    private NewChatViewModel newChatViewModel;
    List<ChatModel> chatList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newChatViewModel = new ViewModelProvider(requireActivity()).get(NewChatViewModel.class);
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initRecyclerView();
        fetchChatsFromDatabase();
        //getChatItems();
        Intent intent = new Intent("SHOW_MENU_ACTION");
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.newchatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_newChatDetails);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatList = new ArrayList<>();
        adapter = new ChatListAdapter(chatList, this);
        recyclerView.setAdapter(adapter);
    }

    private void fetchChatsFromDatabase(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for(DataSnapshot room : dataSnapshot.getChildren()) {
                        String roomKey = room.getKey();
                        if (roomKey.equals("messages")){continue;}
                        else{
                            ChatModel chat = room.getValue(ChatModel.class);
                            chatList.add(chat);
                        }
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    @Override
    public void onChatClick(ChatModel chatItem) {
        Fragment chatScreen = new ChatScreenFragment();
        Bundle bundle = new Bundle();
        bundle.putString("chatName", chatItem.getChatName());
        bundle.putString("roomID", String.valueOf(chatItem.getRoomID()));
        bundle.putString("recvName", chatItem.getRecvName());
        chatScreen.setArguments(bundle);
        //newChatViewModel.setChatName(chatItem.getChatName());
        //newChatViewModel.setRecvName(chatItem.getRecvName());
        //newChatViewModel.setRoomID(String.valueOf(chatItem.getRoomID()));
        //newChatViewModel.setIsNewChat("NO");
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView2,chatScreen, "chat_screen_fragment");
        transaction.addToBackStack(null);
        transaction.commit();
        Log.d("BUNDLE SENT", "BUNDLE HAS BEEN SENT");
    }
}
