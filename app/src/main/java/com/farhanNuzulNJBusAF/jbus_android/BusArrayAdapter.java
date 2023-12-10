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

/**
 * Custom ArrayAdapter for displaying a list of Bus objects in a ListView.
 *
 * <p>
 * This adapter is used to convert a list of Bus objects into a format that can be displayed
 * in a ListView. It inflates a custom layout for each item in the list and sets the appropriate
 * data for each item.
 * </p>
 *
 * <p>
 * The layout for each item is defined in the "bus_view.xml" file.
 * </p>
 *
 * @see android.widget.ArrayAdapter
 * @see android.view.View
 * @see android.widget.TextView
 * @see com.farhanNuzulNJBusAF.jbus_android.model.Bus
 * @see R.layout#bus_view
 * @see LayoutInflater#from(Context)
 * @see LayoutInflater#inflate(int, ViewGroup, boolean)
 * @version 1.0
 */
public class BusArrayAdapter extends ArrayAdapter<Bus> {

    /**
     * Constructor for the BusArrayAdapter.
     *
     * @param context The current context.
     * @param list    The list of Bus objects to be displayed.
     */
    public BusArrayAdapter(@NonNull Context context, List<Bus> list) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, list);
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
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