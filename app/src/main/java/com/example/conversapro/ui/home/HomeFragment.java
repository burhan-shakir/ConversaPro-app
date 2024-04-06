package com.example.conversapro.ui.home;

import static androidx.navigation.Navigation.findNavController;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.conversapro.ui.chatScreen.ChatScreenFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnChatItemClickListener {

    private FragmentHomeBinding binding;
    private NavController navController;
    List<ChatItem> chatItems = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initRecyclerView();
        getChatItems();
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
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ChatListAdapter adapter = new ChatListAdapter(chatItems, this);
        recyclerView.setAdapter(adapter);
    }



    private List<ChatItem> getChatItems() {
        // return a list of ChatItems
        chatItems.add(new ChatItem("Contact Name 1", "Message preview 1"));
        chatItems.add(new ChatItem("Contact Name 2", "Message preview 2"));
        chatItems.add(new ChatItem("Contact Name 3", "Message preview 3"));
        chatItems.add(new ChatItem("Contact Name 4", "Message preview 4"));
        chatItems.add(new ChatItem("Contact Name 5", "Message preview 5"));

        chatItems.add(new ChatItem("Contact Name 6", "Message preview 6"));
        chatItems.add(new ChatItem("Contact Name 7", "Message preview 7"));
        chatItems.add(new ChatItem("Contact Name 8", "Message preview 8"));
        chatItems.add(new ChatItem("Contact Name 9", "Message preview 9"));
        chatItems.add(new ChatItem("Contact Name 10", "Message preview 10"));
        chatItems.add(new ChatItem("test", "Message preview 11"));

        return chatItems;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    @Override
    public void onChatClick(ChatItem chatItem) {
        Fragment chatScreen = new ChatScreenFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView2,chatScreen, "chat_screen_fragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
