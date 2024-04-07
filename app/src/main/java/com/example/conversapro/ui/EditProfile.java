package com.example.conversapro.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.conversapro.R;
import com.example.conversapro.databinding.FragmentEditProfileBinding;
import com.example.conversapro.databinding.FragmentNotificationsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfile#} factory method to
 * create an instance of this fragment.
 */
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
                String account = binding.editAccount.getText().toString();
                String email = binding.editEmail.getText().toString();
                String description = binding.editDescription.getText().toString();
                updateInformation(account, email, description);
            }
        });

        return binding.getRoot(); // Return the inflated view
    }

    private void updateInformation(String newName, String newEmail, String newDescription){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = currentUser.getUid();
        // Update the name field in the database
        if(!newName.isEmpty())
            mDatabase.child(currentUserId).child("name").setValue(newName);
        // Update the email field in the database
        if(!newEmail.isEmpty())
            mDatabase.child(currentUserId).child("email").setValue(newEmail);
        // Update the description field in the database
        mDatabase.child(currentUserId).child("description").setValue(newDescription);
        NavController controller = Navigation.findNavController(getView());
        controller.navigate(R.id.action_editProfile_to_navigation_notifications);
    }
}