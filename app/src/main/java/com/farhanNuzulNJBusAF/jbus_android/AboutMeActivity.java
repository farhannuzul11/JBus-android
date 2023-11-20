package com.farhanNuzulNJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        TextView username = findViewById(R.id.username);
        username.setText("farhan.nuzul");
        TextView email = findViewById(R.id.email);
        email.setText("farhan.nuzul@ui.ac.id");
        TextView balance = findViewById(R.id.balance);
        balance.setText("RP.10.000.000,00-");
    }




}