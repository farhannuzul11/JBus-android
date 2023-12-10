package com.farhanNuzulNJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.farhanNuzulNJBusAF.jbus_android.model.Bus;
import com.farhanNuzulNJBusAF.jbus_android.request.BaseApiService;
import com.farhanNuzulNJBusAF.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The activity for managing buses associated with the user's account.
 *
 * <p>
 * This activity allows users to view and manage their buses. It displays a list of
 * buses that are associated with the user's account. Users can add new buses by
 * clicking the "Add Bus" button. The list of buses is displayed using a custom
 * adapter {@link MyBusArrayAdapter}.
 * </p>
 *
 * <p>
 * The user's buses are retrieved from the server using a Retrofit API call in
 * the {@link #handleGetMyBus()} method. The response is then used to update the
 * list of buses and notify the adapter of the changes.
 * </p>
 *
 * @see MyBusArrayAdapter
 * @see BaseApiService
 * @see UtilsApi
 */
public class ManageBusActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    public MyBusArrayAdapter myBusArrayAdapter;
    private ImageView addBus;
    private ListView myBusListView = null;
    private List<Bus> listBus = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bus);

        try {
            getSupportActionBar().hide();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        mContext = this;
        mApiService = UtilsApi.getApiService();
        handleGetMyBus();

        myBusArrayAdapter = new MyBusArrayAdapter(this, listBus);

        myBusListView = findViewById(R.id.myBusList);
        myBusListView.setAdapter(myBusArrayAdapter);

        addBus = findViewById(R.id.add_bus_button);

        addBus.setOnClickListener(v -> {
            moveActivity(mContext, AddBusActivity.class);
        });
    }

    /**
     * Move to another activity.
     *
     * @param ctx The context from which the activity is started.
     * @param cls The class of the activity to start.
     */
    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    /**
     * Handles the retrieval of buses associated with the user's account from the server.
     */
    protected void handleGetMyBus() {
        // Make the GET request using Retrofit
        mApiService.getBus(LoginActivity.loggedAccount.id).enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Bus> busList = response.body();

                if (busList != null && !busList.isEmpty()) {
                    runOnUiThread(() -> {
                        listBus.clear();
                        listBus.addAll(busList);
                        myBusArrayAdapter.notifyDataSetChanged(); // Notify the adapter of the change
                    });
                } else {
                    Toast.makeText(mContext, "No buses found for the given account ID", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}