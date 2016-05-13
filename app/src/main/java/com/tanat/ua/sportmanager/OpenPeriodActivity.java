package com.tanat.ua.sportmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OpenPeriodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_period);



        Intent intent = getIntent();
        String title = intent.getStringExtra("namePeriod");
        setTitle(title);
    }
}
