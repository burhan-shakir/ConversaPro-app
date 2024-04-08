package com.example.conversapro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
//-------------------------------------------------------------------------------------
//                               SFWRENG 3A04 Deliverable 4
//                                 ConversaPro Application
//                                     T03 - Group 10
//    Kelvin Yu, Ghena Hatoum, Burhan Shakir, Jianqing (Pega) Liu, Xiaotian (Raynor) Lou
//-------------------------------------------------------------------------------------
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


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Hide at startup
        bottomNavigationView.setVisibility(View.GONE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("SHOW_MENU_ACTION"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiverH,
                new IntentFilter("HIDE_MENU_ACTION"));

    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            showMenu();
        }
    };
    private BroadcastReceiver mMessageReceiverH = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            hideMenu();
        }
    };


    private void showMenu(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }
    private void hideMenu(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.GONE);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
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
