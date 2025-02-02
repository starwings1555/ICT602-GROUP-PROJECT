package com.example.barbershop.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetPassword extends AppCompatActivity {
    EditText et_sendEmail;
    Button btn_reset, backButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        et_sendEmail = findViewById(R.id.et_sendEmail);
        btn_reset = findViewById(R.id.btn_reset);
        backButton = findViewById(R.id.backButton);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_sendEmail.getText().toString();
                if (email.equals("")) {
                    Toast.makeText(resetPassword.this, "Email is empty", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(resetPassword.this, "Please Check Your Email", Toast.LENGTH_SHORT).show();
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(resetPassword.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
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
        Intent intent = new Intent(this, login.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

        finish();
    }
}