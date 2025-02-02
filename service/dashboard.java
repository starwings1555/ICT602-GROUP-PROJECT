package com.example.barbershop.service;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class dashboard extends AppCompatActivity {
    Button btn_addAppointment, btn_viewAppointment, btn_location, btn_aboutUs, backButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btn_addAppointment = findViewById(R.id.btn_addAppointment);
        btn_viewAppointment = findViewById(R.id.btn_viewAppointment);
        btn_location = findViewById(R.id.btn_location);
        btn_aboutUs = findViewById(R.id.btn_aboutUs);
        backButton = findViewById(R.id.backButton);

        String restName = "";
        btn_addAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(restName);
            }
        });
        btn_viewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, viewAppointment.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, shopLocation.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        btn_aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, aboutUs.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        progressDialog = new ProgressDialog(dashboard.this);
    }
    private void createDialog(String restName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(dashboard.this);
        View view = LayoutInflater.from(dashboard.this).inflate(R.layout.activity_add_appointment, null, false);
        builder.setView(view);

        EditText et_username = view.findViewById(R.id.et_username);
        EditText et_phoneNumber = view.findViewById(R.id.et_phoneNumber);
        EditText et_date = view.findViewById(R.id.et_date);
        EditText et_time = view.findViewById(R.id.et_time);
        Button btn_addNewAppointment = view.findViewById(R.id.btn_addNewAppointment);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        et_date.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    dashboard.this,
                    (view1, selectedYear, selectedMonth, selectedDay) ->
                            et_date.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear),
                    year, month, day
            );
            datePickerDialog.show();
        });

        et_time.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    dashboard.this,
                    (view12, selectedHour, selectedMinute) ->
                            et_time.setText(String.format("%02d:%02d", selectedHour, selectedMinute)),
                    hour, minute, true
            );
            timePickerDialog.show();
        });

        btn_addNewAppointment.setOnClickListener(v -> {
            String username = et_username.getText().toString();
            String phoneNumber = et_phoneNumber.getText().toString();
            String date = et_date.getText().toString();
            String time = et_time.getText().toString();

            if (username.isEmpty() || phoneNumber.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(dashboard.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.setMessage("Adding Service");
                progressDialog.setTitle("Adding...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                addServiceToFirestore(username, phoneNumber, date, time, restName);

                alertDialog.dismiss();
            }
        });
    }
    private void addServiceToFirestore(String username, String phoneNumber, String date, String time, String restName) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = firebaseUser.getUid();

        CollectionReference servicesCollection = db.collection("services");

        DocumentReference newServiceRef = servicesCollection.document();

        Map<String, Object> data = new HashMap<>();
        data.put("id", userId);
        data.put("username", username);
        data.put("phoneNumber", phoneNumber);
        data.put("date", date);
        data.put("time", time);
        data.put("restName", restName);

        newServiceRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(dashboard.this, "Appointment added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(dashboard.this, "Failed to add appointment", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = new Intent(this, login.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

        finish();
    }
}
