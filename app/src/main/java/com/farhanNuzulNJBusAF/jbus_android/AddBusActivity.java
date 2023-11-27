package com.farhanNuzulNJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    AdapterView.OnItemSelectedListener busTypeOISL = new
            AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                    selectedBusType = busType[position];
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            };
    AdapterView.OnItemSelectedListener deptOISL = new
            AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                    selectedDeptStationID = stationList.get(position).id;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            };

    AdapterView.OnItemSelectedListener arrOISL = new
            AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                    selectedArrStationID = stationList.get(position).id;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        acCheckBox = findViewById(R.id.ac);
        wifiCheckBox = findViewById(R.id.wifi);
        toiletCheckBox = findViewById(R.id.toilet);
        lcdCheckBox = findViewById(R.id.lcd_tv);
        coolboxCheckBox = findViewById(R.id.coolbox);
        lunchCheckBox = findViewById(R.id.lunch);
        baggageCheckBox = findViewById(R.id.baggage);
        electricCheckBox= findViewById(R.id.electric_socket);
        busName = findViewById(R.id.bus_name);
        capacity = findViewById(R.id.capacity);
        price = findViewById(R.id.price);

        handleGetAllStation();
        handleFacilities();

        busTypeSpinner = this.findViewById(R.id.bus_type_dropdown);
        departureSpinner = this.findViewById(R.id.departure_dropdown);
        arrivalSpinner = this.findViewById(R.id.arrival_dropdown);

        ArrayAdapter adBus = new ArrayAdapter(this, android.R.layout.simple_list_item_1, busType);
        adBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        busTypeSpinner.setAdapter(adBus);

        busTypeSpinner.setOnItemSelectedListener(busTypeOISL);
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

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

    protected void handleAddBus() {
        String busNameS = busName.getText().toString();
        String capacityS = capacity.getText().toString();
        String priceS = price.getText().toString();
        String busTypeS = busType.toString();
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