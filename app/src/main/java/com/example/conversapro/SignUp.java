package com.example.conversapro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.nullness.qual.NonNull;

public class SignUp extends AppCompatActivity {

    EditText edtName, edtEmail, edtPassword;
    Button btnSignUp;
    FirebaseAuth authen;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        authen = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnSignUp = findViewById(R.id.btn_signup);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String name = edtName.getText().toString();
                String password = edtPassword.getText().toString();
                signUp(name, email, password);
            }
        });
    }

    private void signUp(String name, String email, String password){
        System.out.println(name + " " + email + " " + password);
        authen.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            addUser(name, email, authen.getCurrentUser().getUid());
                        } else {
                            // If sign in fails, display a message to the user.
                            if (task.getException() != null) {
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(SignUp.this, "Sign up failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }
    private void addUser(String name, String email, String uid){
        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child("users").child(uid).child("email").setValue(email);
        dbRef.child("users").child(uid).child("name").setValue(name);
        dbRef.child("users").child(uid).child("description").setValue("");
    }
}