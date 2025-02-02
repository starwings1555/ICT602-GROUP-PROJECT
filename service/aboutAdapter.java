package com.example.barbershop.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barbershop.R;
public class aboutAdapter extends BaseAdapter {
    private Context mContext;
    private String[] names = {
            "Muhammad Akmal Haikal Bin Mohd Idrus" ,
            "Muhammad Ariq Irfan Bin Mohd Rosidi",
            "Muhammad Firdaus Bin Najib",
            "Azrul Hanafi Bin Ramle"
    };
    private String[] studentIds = {
            "2022663394",
            "2021657832",
            "2022851474",
            "2022616296"
    };
    private String[] programs = {"CS2405A", "CS2405A", "CS2405A", "CS2405A"};
    private int[] images = {
            R.drawable.picture_haikal,
            R.drawable.picture_ariq,
            R.drawable.picture_daus,
            R.drawable.picture_azrul
    };
    public aboutAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_about_us, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.image_view);
            holder.nameTextView = convertView.findViewById(R.id.name_text_view);
            holder.idTextView = convertView.findViewById(R.id.id_text_view);
            holder.programTextView = convertView.findViewById(R.id.program_text_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageView.setImageResource(images[position]);
        holder.nameTextView.setText(names[position]);
        holder.idTextView.setText(studentIds[position]);
        holder.programTextView.setText(programs[position]);

        return convertView;
    }
    static class ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView idTextView;
        TextView programTextView;
    }
}


