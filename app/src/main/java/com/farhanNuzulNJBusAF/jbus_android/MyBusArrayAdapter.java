package com.farhanNuzulNJBusAF.jbus_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.farhanNuzulNJBusAF.jbus_android.model.Bus;

//public class MyBusArrayAdapter extends ArrayAdapter<Bus> {
//    private Context context;
//    public MyBusArrayAdapter(@NonNull Context context, List<Bus> list) {
//
//        // pass the context and list for the super
//        // constructor of the ArrayAdapter class
//        super(context, 0, list);
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        View currentItemView = convertView;
//
//        if (currentItemView == null) {
//            // di sini terhubung dengan my_bus_view
//            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.my_bus_view, parent, false);
//        }
//
//        Bus currentNumberPosition = getItem(position);
//
//        TextView textView1 = currentItemView.findViewById(R.id.textView1);
//        textView1.setText(currentNumberPosition.getName());
//
//        ImageView calendar = currentItemView.findViewById(R.id.calendar);
//        calendar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, BusScheduleActivity.class);
//                context.startActivity(intent);
//            }
//        });
//
//        return currentItemView;
//    }
//}