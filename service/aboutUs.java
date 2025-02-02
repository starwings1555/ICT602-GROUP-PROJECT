package com.example.barbershop.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.R;

public class aboutUs extends AppCompatActivity {
    GridView gridView;
    TextView aboutUsHeading;
    TextView aboutUsDescription;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        gridView = findViewById(R.id.gridView);
        aboutUsHeading = findViewById(R.id.aboutUsHeading);
        aboutUsDescription = findViewById(R.id.aboutUsDescription);
        backButton = findViewById(R.id.backButton);

        aboutAdapter aboutAdapter = new aboutAdapter(this);
        gridView.setAdapter(aboutAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = new Intent(this, dashboard.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

        finish();
    }
}