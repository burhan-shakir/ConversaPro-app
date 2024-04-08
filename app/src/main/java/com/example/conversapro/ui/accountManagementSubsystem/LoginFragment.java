package com.example.conversapro.ui.accountManagementSubsystem;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conversapro.KerberosProtocol.Client;
import com.example.conversapro.databinding.FragmentLoginBinding;

import com.example.conversapro.R;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;
    private Client client;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final Button registerButton = binding.register;
        Intent intent = new Intent("HIDE_MENU_ACTION");
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

        loginViewModel.getLoginFormState().observe(getViewLifecycleOwner(), new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loginResult) {
                if (loginResult == null) {
                    return;
                }
                if (loginResult) {
                    loginSuccess();
                } else {
                    showLoginFailed();
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String email = usernameEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    login(email, password);
                }
                return false;
            }
        });

        //goes to registration page for when the button is pressed
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(getView());
                controller.navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });

        //authenticates user when they press login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take user information
                String email = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                //go to login function to check
                login(email, password);
            }
        });

        //if user can login it goes to loginSuccess()
        client = Client.getInstance();
        client.readKeys(getContext());
        if (client.isLoggedIn()) {
            loginSuccess();
        }
    }
    //goes to the home screen
    private void loginSuccess() {
        NavController controller = Navigation.findNavController(getView());
        controller.navigate(R.id.action_loginFragment_to_homeFragment);
    }
    //shows a message saying that you can't sign in
    private void showLoginFailed() {
        Toast.makeText(getContext().getApplicationContext(), "Authentication failed: Unknown error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void login(String email, String password) {
        client.authenticateWithChatService(getContext(), email, password, success -> {
            this.loginViewModel.setLoginResult(success);
            return null;
        });
    }
}