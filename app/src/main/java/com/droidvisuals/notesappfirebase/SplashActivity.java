package com.droidvisuals.notesappfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /*
            Handler is an Android class that allows you to schedule code to run at a later time.
            The postDelayed method takes two arguments: a Runnable (which contains the code you want to run) and the delay time in milliseconds.
        */

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if(currentUser == null ){
                    // no user found who is logged in (  )
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                }
                else{
                    // logged in  already
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }

                finish();
            }

        },2000);

    }
}