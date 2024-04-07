package com.example.conversapro.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.conversapro.R;
import com.example.conversapro.databinding.FragmentNotificationsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private DatabaseReference mDatabase;
    String account, email, description;
    TextView accountText, emailText, descriptionText;
    Button editButton;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        accountText = binding.txtAccount;
        emailText = binding.txtEmail;
        descriptionText = binding.txtDescription;
        editButton = binding.btnEdit;
        mDatabase.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fillInformation(snapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(getView());
                controller.navigate(R.id.action_navigation_notifications_to_editProfileFragment);
            }
        });
        View root = binding.getRoot();
        return root;
    }
    public void fillInformation(DataSnapshot snapshot){
        account = snapshot.child("name").getValue(String.class);
        email = snapshot.child("email").getValue(String.class);
        description = snapshot.child("description").getValue(String.class);
        accountText.setText(account);
        emailText.setText(email);
        descriptionText.setText(description);
    }
}