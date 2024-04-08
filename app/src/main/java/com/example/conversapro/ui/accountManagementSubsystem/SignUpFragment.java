package com.example.conversapro.ui.accountManagementSubsystem;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.conversapro.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Base64;


public class SignUpFragment extends Fragment {

    private EditText edtName, edtEmail, edtPassword;
    private Button btnSignUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        //connect to the xml file
        edtName = view.findViewById(R.id.edt_name);
        edtEmail = view.findViewById(R.id.edt_email);
        edtPassword = view.findViewById(R.id.edt_password);
        btnSignUp = view.findViewById(R.id.btn_signup);
        //when you press the sign up button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //takes user information and sign up
                String email = edtEmail.getText().toString();
                String name = edtName.getText().toString();
                String password = edtPassword.getText().toString();
                signUp(name, email, password);
            }
        });

        return view;
    }

    private void signUp(String name, String email, String password) {
        //get an instance for the database
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        UserModel user = new UserModel(name, email, password, "");
        //add information to database if possible
        db.child("users").child(Base64.getEncoder().encodeToString(email.getBytes())).setValue(user).addOnCompleteListener(task -> {
            //if information added to the database move to home screen
            if (task.isSuccessful()) {
                NavController controller = Navigation.findNavController(getView());
                controller.navigate(R.id.action_signUpFragment_to_homeFragment);
            } else {
                //if you can't show a message showing the error
                if (task.getException() != null) {
                    String errorMessage = task.getException().getMessage();
                    Toast.makeText(requireContext(), "Sign up failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
