package com.farhanNuzulNJBusAF.jbus_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.farhanNuzulNJBusAF.jbus_android.model.Bus;

import java.util.List;

/**
 * Custom ArrayAdapter for displaying buses in a list view.
 *
 * <p>
 * This ArrayAdapter is designed to work with the {@link ManageBusActivity} to display a
 * list of buses associated with the user's account. It overrides the {@link #getView(int, View, ViewGroup)}
 * method to customize the appearance of each list item. Each item includes the bus name and a calendar
 * icon that, when clicked, opens the {@link BusScheduleActivity}.
 * </p>
 *
 * @see Bus
 * @see ManageBusActivity
 * @see BusScheduleActivity
 */
public class MyBusArrayAdapter extends ArrayAdapter<Bus> {
    private Context context;

    /**
     * Constructor for the MyBusArrayAdapter.
     *
     * @param context The context from which the adapter is created.
     * @param list    The list of buses to be displayed.
     */
    public MyBusArrayAdapter(@NonNull Context context, List<Bus> list) {
        super(context, 0, list);
        this.context = context;
    }

    /**
     * Overrides the getView method to customize the appearance of each list item.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The recycled view to populate.
     * @param parent      The parent view that the returned view will be attached to.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_my_bus_array_adapter, parent, false);
        }

        Bus currentNumberPosition = getItem(position);

        TextView textView1 = currentItemView.findViewById(R.id.textView1);
        textView1.setText(currentNumberPosition.getName());

        ImageView calendar = currentItemView.findViewById(R.id.calendar);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BusScheduleActivity.class);
                context.startActivity(intent);
            }
        });

        return currentItemView;
    }
}