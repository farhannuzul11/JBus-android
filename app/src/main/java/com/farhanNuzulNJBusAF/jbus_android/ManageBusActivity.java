package com.farhanNuzulNJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.farhanNuzulNJBusAF.jbus_android.model.Bus;
import com.farhanNuzulNJBusAF.jbus_android.request.BaseApiService;
import com.farhanNuzulNJBusAF.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

public class ManageBusActivity extends AppCompatActivity {
//    private Context  mContext;
//    private BaseApiService mApiService;
//    private List<Bus> listBus = new ArrayList<>();
//    private ImageView addBus;
//    public MyBusArrayAdapter myBusArrayAdapter;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_manage_bus);
//
//        mContext = this;
//        mApiService = UtilsApi.getApiService();
//        handleGetMyBus();
//        myBusArrayAdapter = new MyBusArrayAdapter(this, listBus);
//
//        myBusListView = findViewById(R.id.myBusList);
//        myBusListView.setAdapter(myBusArrayAdapter);
//
//        addBus = findViewById(R.id.add_bus);
//
//        addBus.setOnClickListener(v -> {
//            moveActivity(mContext, AddBusActivity.class);
//        });
//    }
//
//    private void moveActivity(Context ctx, Class<?> cls) {
//        Intent intent = new Intent(ctx, cls);
//        startActivity(intent);
//    }
//
//    protected void handleGetMyBus() {
//        // Make the GET request using Retrofit
//        mApiService.getMyBus(LoginActivity.loggedAccount.id).enqueue(new Callback<List<Bus>>() {
//            @Override
//            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                List<Bus> busList = response.body();
//
//                if (busList != null && !busList.isEmpty()) {
//                    runOnUiThread(() -> {
//                        listBus.clear();
//                        listBus.addAll(busList);
//                        myBusArrayAdapter.notifyDataSetChanged(); // Notify the adapter of the change
//                    });
//                } else {
//                    Toast.makeText(mContext, "No buses found for the given account ID", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Bus>> call, Throwable t) {
//                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}