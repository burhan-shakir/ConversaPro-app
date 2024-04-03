package com.example.conversapro;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        //Title Progressive
//        TextView textTitle = findViewById(R.id.textViewtilte);
//        Animation animationTitle = AnimationUtils.loadAnimation(MainActivity.this, R.anim.text_title_ani);
//        textTitle.startAnimation(animationTitle);
//
//        //logo Progressive
//        ImageView logo = findViewById(R.id.logoImage);
//        Animation animationLogo = AnimationUtils.loadAnimation(MainActivity.this, R.anim.logo_ani);
//        logo.startAnimation(animationLogo);

//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 4500);


    }
}