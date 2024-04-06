package com.example.conversapro.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
import com.example.conversapro.SignUp;
import com.example.conversapro.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    private EditText edtName, edtEmail, edtPassword;
    private Button btnSignUp;
    private FirebaseAuth authen;
    private DatabaseReference dbRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        authen = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        edtName = view.findViewById(R.id.edt_name);
        edtEmail = view.findViewById(R.id.edt_email);
        edtPassword = view.findViewById(R.id.edt_password);
        btnSignUp = view.findViewById(R.id.btn_signup);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String name = edtName.getText().toString();
                String password = edtPassword.getText().toString();
                signUp(name, email, password);
            }
        });

        return view;
    }

    private void signUp(String name, String email, String password){
        authen.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            addUser(name, email, authen.getCurrentUser().getUid());
                            NavController controller = Navigation.findNavController(getView());
                            controller.navigate(R.id.action_signUpFragment_to_homeFragment);
                        } else {
                            if (task.getException() != null) {
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(requireContext(), "Sign up failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }

    private void addUser(String name, String email, String uid){
        dbRef.child("users").child(uid).child("email").setValue(email);
        dbRef.child("users").child(uid).child("name").setValue(name);
        dbRef.child("users").child(uid).child("description").setValue("");
    }
}
