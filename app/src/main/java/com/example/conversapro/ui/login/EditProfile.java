package com.example.conversapro.ui.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.conversapro.KerberosProtocol.Client;
import com.example.conversapro.R;
import com.example.conversapro.databinding.FragmentEditProfileBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Base64;

public class EditProfile extends Fragment {
    private FragmentEditProfileBinding binding;
    private DatabaseReference mDatabase;
    private Button updateBtn; // Declare the updateBtn here

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);

        // Initialize the database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        // Initialize the updateBtn
        updateBtn = binding.updateBtn;

        // Set click listener for the update button
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the new values
                String account = binding.editAccount.getText().toString();
                String description = binding.editDescription.getText().toString();
                //update database
                updateInformation(account, description);
            }
        });

        return binding.getRoot(); // Return the inflated view
    }

    private void updateInformation(String newName, String newDescription){
        Client client = Client.getInstance();
        String currentUserId = client.getUid();
        // Update the name field in the database
        if(!newName.isEmpty())
            mDatabase.child(currentUserId).child("name").setValue(newName);
        // Update the description field in the database
        mDatabase.child(currentUserId).child("description").setValue(newDescription);
        NavController controller = Navigation.findNavController(getView());
        //move back to notification
        controller.navigate(R.id.action_editProfile_to_navigation_notifications);
    }
}