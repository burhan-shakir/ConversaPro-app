package com.example.conversapro.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.IntentCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.conversapro.KerberosProtocol.Client;
import com.example.conversapro.MainActivity;
import com.example.conversapro.R;
import com.example.conversapro.databinding.FragmentNotificationsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Base64;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    String account, email, description;
    TextView accountText, emailText, descriptionText;
    Button editButton, logoutButton;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        Client client=Client.getInstance();
        String uid = client.getUid();
        accountText = binding.txtAccount;
        emailText = binding.txtEmail;
        descriptionText = binding.txtDescription;
        editButton = binding.btnEdit;
        logoutButton = binding.btnLogout;
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
                controller.navigate(R.id.action_navigation_notifications_to_editProfile);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Client client = Client.getInstance();
                client.logout(getContext());
                Activity activity = getActivity();
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                activity.finishAffinity();
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