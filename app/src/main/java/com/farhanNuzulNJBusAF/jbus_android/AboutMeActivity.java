package com.farhanNuzulNJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.farhanNuzulNJBusAF.jbus_android.model.Account;
import com.farhanNuzulNJBusAF.jbus_android.model.BaseResponse;
import com.farhanNuzulNJBusAF.jbus_android.request.BaseApiService;
import com.farhanNuzulNJBusAF.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
    private Context mContext;
    private BaseApiService mApiService;
    private Button topUp;
    private EditText amount;
    private TextView username, email, balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        balance = findViewById(R.id.balance);
        amount = findViewById(R.id.top_up_amount);
        topUp = findViewById(R.id.top_up_button);

        username.setText(LoginActivity.loggedAccount.name);
        email.setText(LoginActivity.loggedAccount.email);
        balance.setText(Double.toString(LoginActivity.loggedAccount.balance));



        topUp.setOnClickListener(v -> {
            handleTopUp();
        });

        renterSection();

    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void renterSection() {
        LinearLayout layout = findViewById(R.id.renter_layout);
        TextView renter_s = new TextView(this);

        renter_s.setTextColor(getResources().getColor(R.color.black));
        renter_s.setTextSize(16);

        if(LoginActivity.loggedAccount.company == null) {
            TextView registerCompany = new TextView(this);
            renter_s.setTextSize(20);
            registerCompany.setTextColor(getResources().getColor(R.color.black));


            renter_s.setText("You're not registered as a renter");
            registerCompany.setText("Register Here");
            registerCompany.setTypeface(null, Typeface.BOLD);

            registerCompany.setOnClickListener(v -> {
                moveActivity(this, RegisterRenterActivity.class);
            });

            LinearLayout.LayoutParams layout_p = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            layout.addView(renter_s, layout_p);
            layout.addView(registerCompany, layout_p);

        } else {
            Button manageBus = new Button(this);

            renter_s.setText("You're already registered as renter");
            manageBus.setText("Manage Bus");
            manageBus.setTextSize(16);

            LinearLayout.LayoutParams layout_p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            );

            manageBus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    moveActivity(mContext, ManageBusActivity.class);
                }
            });

            layout.addView(renter_s, layout_p);
            layout.addView(manageBus, layout_p);
        }
    }



    protected void handleTopUp() {
        String amount_handle = amount.getText().toString();
        if (amount_handle.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.topUp(LoginActivity.loggedAccount.id, Double.parseDouble(amount_handle)).enqueue(new Callback<BaseResponse<Double>>() {
            @Override
            public void onResponse(Call<BaseResponse<Double>> call, Response<BaseResponse<Double>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Double> res = response.body();
                if (res.success) {
                    LoginActivity.loggedAccount.balance = Double.parseDouble(amount_handle) + LoginActivity.loggedAccount.balance;
                    finish();
                }
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<BaseResponse<Double>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}