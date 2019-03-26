package com.example.tryapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class SelfModeActivity extends AppCompatActivity {

    private Button sendSOS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_mode);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sendSOS = findViewById(R.id.button4);
        sendSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SOS mySos = new SOS("C1","P1","CHILD","PARENT","Emergency");
               new RealTimeDBHandler(SelfModeActivity.this).onCreateReference(mySos);
            }
        });
    }
}
