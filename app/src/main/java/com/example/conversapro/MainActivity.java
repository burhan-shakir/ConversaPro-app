package com.example.conversapro;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 1); // bottom padding is set to 1 for visuals
            return insets;
        });

        // Check if the user is logged in before setting up the navigation
        if (userIsLoggedIn()) {
            setupNavigationWhenReady(R.id.fragmentContainerView2);
        }
    }

    private void setupNavigationWhenReady(int fragmentContainerView2Id) {
        final View navHostFragment = findViewById(fragmentContainerView2Id);

        // Ensure the NavHostFragment is fully attached before setting up navigation
        navHostFragment.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                navHostFragment.removeOnAttachStateChangeListener(this);
                NavController navController = Navigation.findNavController(MainActivity.this, fragmentContainerView2Id);
                BottomNavigationView navView = findViewById(R.id.bottom_navigation);
                NavigationUI.setupWithNavController(navView, navController);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                // No operation
            }
        });
    }

    // Method to check if the user is logged in
    private boolean userIsLoggedIn() {
        // logic for user login or something

        return true; // assume true
    }
}
