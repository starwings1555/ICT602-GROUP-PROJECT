package com.example.barbershop.service;
public class appointmentModel {
    String username, phoneNumber, date, time;

    public appointmentModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public appointmentModel(String username, String phoneNumber, String date, String time) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.time = time;
    }
}

