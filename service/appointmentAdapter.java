package com.example.barbershop.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class appointmentAdapter extends RecyclerView.Adapter<appointmentAdapter.viewHolder> {
    private Context mContext;
    private List<appointmentModel> mList = new ArrayList<>();

    private ProgressDialog progressDialog;

    public appointmentAdapter(Context mContext, List<appointmentModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
        progressDialog = new ProgressDialog(mContext);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_layout_appointment, parent, false);
        return new viewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        appointmentModel model = mList.get(position);
        holder.tv_username.setText(model.getUsername());
        holder.tv_phoneNumber.setText(model.getPhoneNumber());
        holder.tv_date.setText(model.getDate());
        holder.tv_time.setText(model.getTime());

    }
    public void markAsComplete(appointmentModel model) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = firebaseUser.getUid();

        DocumentReference documentReference = db.collection("services").document(model.getUsername());

        Map<String, Object> data = new HashMap<>();
        data.put("id", userId);
        data.put("Name", model.getUsername());
        data.put("Phone Number", model.getPhoneNumber());
        data.put("Date", model.getDate());
        data.put("Time", model.getTime());

        documentReference.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(mContext, "Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
    class viewHolder extends RecyclerView.ViewHolder {

        private TextView tv_username;
        private TextView tv_phoneNumber;
        private TextView tv_date;
        private TextView tv_time;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tv_username = itemView.findViewById(R.id.tv_username);
            tv_phoneNumber = itemView.findViewById(R.id.tv_phoneNumber);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
}

