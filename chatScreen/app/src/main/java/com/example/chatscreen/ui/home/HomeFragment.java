package com.example.chatscreen.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatscreen.R;
import com.example.chatscreen.databinding.FragmentHomeBinding;
import com.example.chatscreen.ui.adapter.ChatAdapter;
import com.example.chatscreen.ui.adapter.ChatItem;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initRecyclerView();
        return binding.getRoot();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<ChatItem> chatItems = getChatItems();

        ChatAdapter adapter = new ChatAdapter(chatItems, chatItem -> {
            Bundle bundle = new Bundle();
            bundle.putString("contactName", chatItem.getContactName());
            bundle.putString("messagePreview", chatItem.getMessagePreview());
            NavHostFragment.findNavController(HomeFragment.this)
                    .navigate(R.id.action_homeFragment_to_chatDetailFragment, bundle);
        });
        recyclerView.setAdapter(adapter);
    }


    private List<ChatItem> getChatItems() {
        // return a list of ChatItems

        List<ChatItem> chatItems = new ArrayList<>();
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
        chatItems.add(new ChatItem("Contact Name 11", "Message preview 11"));

        return chatItems;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
