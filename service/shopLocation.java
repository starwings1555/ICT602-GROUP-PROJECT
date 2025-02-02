package com.example.barbershop.service;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.barbershop.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class shopLocation extends AppCompatActivity {

    SupportMapFragment smf_googleMap;
    FusedLocationProviderClient client;
    Button backButton;

    private static final int REQUEST_LOCATION = 100;
    Double latitude;
    Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_location);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        smf_googleMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_maps);
        client = LocationServices.getFusedLocationProviderClient(this);
        getMyLocation();
        backButton = findViewById(R.id.backButton);

        double[] serviceLatitudes = {3.074146, 3.06306, 3.05721, 3.05803};
        double[] serviceLongitudes = {101.518580, 101.482021, 101.48984, 101.53972};

        int selectedCoordinatesIndex = 1;

        latitude = serviceLatitudes[selectedCoordinatesIndex];
        longitude = serviceLongitudes[selectedCoordinatesIndex];


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(shopLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getMyLocation();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            }
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getMyLocation();
            }
        }
    }

    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                smf_googleMap.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {

                        List<MarkerOptions> markerOptionsList = new ArrayList<>();

                        double[] serviceLatitudes = {3.074146, 3.06306, 3.05721, 3.05803};
                        double[] serviceLongitudes = {101.518580, 101.482021, 101.48984, 101.53972};
                        for (int i = 0; i < serviceLatitudes.length; i++) {
                            LatLng latLng = new LatLng(serviceLatitudes[i], serviceLongitudes[i]);
                            MarkerOptions markerOptions = new MarkerOptions()
                                    .position(latLng)
                                    .title("BAPPERS Shop " + (i + 1))
                                    .snippet("Open: From 2:00pm - 11:00pm");

                            markerOptionsList.add(markerOptions);

                            googleMap.addMarker(markerOptions);
                        }

                        LatLng selectedLatLng = new LatLng(latitude, longitude);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedLatLng, 12));

                    }
                });
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
