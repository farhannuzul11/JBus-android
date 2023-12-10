package com.farhanNuzulNJBusAF.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.farhanNuzulNJBusAF.jbus_android.model.BaseResponse;
import com.farhanNuzulNJBusAF.jbus_android.model.Bus;
import com.farhanNuzulNJBusAF.jbus_android.model.BusType;
import com.farhanNuzulNJBusAF.jbus_android.model.Facility;
import com.farhanNuzulNJBusAF.jbus_android.model.Station;
import com.farhanNuzulNJBusAF.jbus_android.request.BaseApiService;
import com.farhanNuzulNJBusAF.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity class for adding a new bus to the system.
 *
 * <p>
 * This activity allows the user to input details such as bus name, capacity, price, facilities, and type.
 * It also provides a user interface to select departure and arrival stations.
 * </p>
 *
 * <p>
 * The user can specify facilities by checking checkboxes for options like AC, WIFI, etc.
 * </p>
 *
 * @author Farhan Nuzul
 * @version 1.0
 */
public class AddBusActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    private EditText busName, capacity, price;
    private BusType[] busType = BusType.values();
    private BusType selectedBusType;
    private Spinner busTypeSpinner;
    private Spinner departureSpinner;
    private Spinner arrivalSpinner;
    private List<Station> stationList = new ArrayList<>();
    private int selectedDeptStationID;
    private int selectedArrStationID;
    private CheckBox acCheckBox, wifiCheckBox, toiletCheckBox, lcdCheckBox;
    private CheckBox coolboxCheckBox, lunchCheckBox, baggageCheckBox, electricCheckBox;
    private List<Facility> selectedFacilities = new ArrayList<>();
    private Button addBusButton;

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState A Bundle containing the data most recently supplied in onSaveInstanceState(Bundle).
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        busName = findViewById(R.id.bus_name);
        capacity = findViewById(R.id.capacity);
        price = findViewById(R.id.price);

        busTypeSpinner = this.findViewById(R.id.bus_type_dropdown);
        departureSpinner = this.findViewById(R.id.departure_dropdown);
        arrivalSpinner = this.findViewById(R.id.arrival_dropdown);

        acCheckBox = findViewById(R.id.ac);
        wifiCheckBox = findViewById(R.id.wifi);
        toiletCheckBox = findViewById(R.id.toilet);
        lcdCheckBox = findViewById(R.id.lcd_tv);
        coolboxCheckBox = findViewById(R.id.coolbox);
        lunchCheckBox = findViewById(R.id.lunch);
        baggageCheckBox = findViewById(R.id.baggage);
        electricCheckBox= findViewById(R.id.electric_socket);

        ArrayAdapter adBus = new ArrayAdapter(this, android.R.layout.simple_list_item_1, busType);
        adBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        busTypeSpinner.setAdapter(adBus);

        busTypeSpinner.setOnItemSelectedListener(busTypeOISL);

        addBusButton = findViewById(R.id.add_bus);

        handleGetAllStation();

        addBusButton.setOnClickListener(v -> {
            handleFacilities();
            handleAddBus();
        });

    /**
    * Handles the selection of the bus type, departure, and arrival spinner.
    */
    }
    AdapterView.OnItemSelectedListener busTypeOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            // mengisi field selectedBusType sesuai dengan item yang dipilih
            selectedBusType = busType[position];
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    AdapterView.OnItemSelectedListener deptOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position,
                                   long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            // mengisi field selectedBusType sesuai dengan item yang dipilih
            selectedDeptStationID = stationList.get(position).id;
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    AdapterView.OnItemSelectedListener arrOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position,
                                   long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            // mengisi field selectedBusType sesuai dengan item yang dipilih
            selectedArrStationID = stationList.get(position).id;
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    /**
     * Moves to another activity.
     *
     * @param ctx The context of the current activity.
     * @param cls The class of the activity to move to.
     */
    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    /**
     * Retrieves all available stations from the server and populates the departure and arrival spinners.
     */
    protected void handleGetAllStation() {
        // Make the GET request using Retrofit
        mApiService.getAllStation().enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                stationList = response.body(); //simpan response body ke listStation

                ArrayAdapter deptBus = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, stationList);
                deptBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                departureSpinner.setAdapter(deptBus);

                departureSpinner.setOnItemSelectedListener(deptOISL);

                ArrayAdapter arrBus = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, stationList);
                arrBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                arrivalSpinner.setAdapter(arrBus);
                arrivalSpinner.setOnItemSelectedListener(arrOISL);

            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Handles the selection of facilities based on checkboxes.
     */
    protected void handleFacilities() {
        selectedFacilities.clear(); // Clear the list before updating
        if (acCheckBox.isChecked()) { selectedFacilities.add(Facility.AC); }
        if (wifiCheckBox.isChecked()) { selectedFacilities.add(Facility.WIFI); }
        if (toiletCheckBox.isChecked()) { selectedFacilities.add(Facility.TOILET); }
        if (lcdCheckBox.isChecked()) { selectedFacilities.add(Facility.LCD_TV); }
        if (coolboxCheckBox.isChecked()) { selectedFacilities.add(Facility.COOL_BOX); }
        if (lunchCheckBox.isChecked()) { selectedFacilities.add(Facility.LUNCH); }
        if (baggageCheckBox.isChecked()) { selectedFacilities.add(Facility.LARGE_BAGGAGE); }
        if (electricCheckBox.isChecked()) { selectedFacilities.add(Facility.ELECTRIC_SOCKET); }
    }

    /**
     * Handles the addition of a new bus to the system.
     */
    protected void handleAddBus() {
        String busNameS = busName.getText().toString();
        String capacityS = capacity.getText().toString();
        String priceS = price.getText().toString();
        if(busNameS.isEmpty() || capacityS.isEmpty() || priceS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.create(LoginActivity.loggedAccount.id, busNameS, Integer.parseInt(capacityS), selectedFacilities, selectedBusType, Integer.parseInt(priceS), selectedDeptStationID, selectedArrStationID).enqueue(new Callback<BaseResponse<Bus>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bus>> call, Response<BaseResponse<Bus>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Bus> res = response.body();
                if (res.success) {
                    Toast.makeText(mContext, "Berhasil menambahkan bus", Toast.LENGTH_SHORT);
                    moveActivity(mContext, ManageBusActivity.class);
                    finish();
                }
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse<Bus>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

