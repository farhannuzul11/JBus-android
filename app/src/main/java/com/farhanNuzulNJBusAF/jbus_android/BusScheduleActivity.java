package com.farhanNuzulNJBusAF.jbus_android;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity class for displaying the schedule of a bus.
 *
 * <p>
 * This activity displays the schedule of a bus and is associated with the "activity_bus_schedule.xml" layout.
 * </p>
 *
 * @see androidx.appcompat.app.AppCompatActivity
 * @version 1.0
 */
public class BusScheduleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_schedule);
    }
}