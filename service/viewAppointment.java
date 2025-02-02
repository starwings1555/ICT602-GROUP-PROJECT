package com.example.barbershop.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class viewAppointment extends AppCompatActivity {

    private List<appointmentModel> mList = new ArrayList<>();
    private RecyclerView recyclerView;
    private appointmentAdapter mAdapter;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);

        recyclerView = findViewById(R.id.rv_showAllService);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(viewAppointment.this));
        backButton = findViewById(R.id.backButton);

        mAdapter = new appointmentAdapter(viewAppointment.this, mList);
        recyclerView.setAdapter(mAdapter);

        getAllServices();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void getAllServices() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference servicesRef = db.collection("services");

            servicesRef.whereEqualTo("id", firebaseUser.getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                mList.clear();
                                for (DocumentSnapshot document : task.getResult()) {
                                    appointmentModel model = document.toObject(appointmentModel.class);
                                    mList.add(model);
                                }
                                mAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(viewAppointment.this, "No services found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Exception e = task.getException();
                            if (e != null) {
                                e.printStackTrace();
                                Toast.makeText(viewAppointment.this, "Error reading data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(viewAppointment.this, "Error reading data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(viewAppointment.this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = new Intent(this, dashboard.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

        finish();
    }

}
