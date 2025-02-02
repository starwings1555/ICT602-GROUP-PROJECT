package com.example.barbershop.service;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.barbershop.R;

import java.util.Calendar;

public class addAppointment extends AppCompatActivity {

    private EditText etUsername, etPhoneNumber, etDate, etTime;
    private Button btnAddNewAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        etUsername = findViewById(R.id.et_username);
        etPhoneNumber = findViewById(R.id.et_phoneNumber);
        etDate = findViewById(R.id.et_date);
        etTime = findViewById(R.id.et_time);
        btnAddNewAppointment = findViewById(R.id.btn_addNewAppointment);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        btnAddNewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAppointment();
            }
        });
    }
    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    etDate.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
    private void showTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, selectedHour, selectedMinute) -> {
                    String selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                    etTime.setText(selectedTime);
                },
                hour, minute, true
        );
        timePickerDialog.show();
    }
    private void createAppointment() {
        String username = etUsername.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String time = etTime.getText().toString().trim();

        if (username.isEmpty() || phoneNumber.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Appointment Created Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}
