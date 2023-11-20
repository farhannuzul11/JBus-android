package com.farhanNuzulNJBusAF.jbus_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.farhanNuzulNJBusAF.jbus_android.model.Bus;
import java.util.ArrayList;
import java.util.List;

public class BusArrayAdapter extends ArrayAdapter<Bus> {

    // invoke the suitable constructor of the ArrayAdapter class
    public BusArrayAdapter(@NonNull Context context, List<Bus> list) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.bus_view, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        Bus currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired TextView 1 for the same
        TextView textView1 = currentItemView.findViewById(R.id.textView1);
        textView1.setText(currentNumberPosition.getName());

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView textView2 = currentItemView.findViewById(R.id.textView2);
        textView2.setText(currentNumberPosition.getType());

        // then return the recyclable view
        return currentItemView;
    }
}