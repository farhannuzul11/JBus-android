package com.farhanNuzulNJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.farhanNuzulNJBusAF.jbus_android.model.Account;
import com.farhanNuzulNJBusAF.jbus_android.model.BaseResponse;
import com.farhanNuzulNJBusAF.jbus_android.model.Renter;
import com.farhanNuzulNJBusAF.jbus_android.request.BaseApiService;
import com.farhanNuzulNJBusAF.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRenterActivity extends AppCompatActivity {

    private BaseApiService mApiService;
    private Context mContext;
    private EditText companyname, address, phoneNumber;
    private Button registerButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_renter);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        companyname = findViewById(R.id.CompanyName);
        address = findViewById(R.id.Address);
        phoneNumber = findViewById(R.id.PhoneNumber);
        registerButton = findViewById(R.id.register_now);

        registerButton.setOnClickListener(v -> {
            handleRegisterRenter();
        });
    }

    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent (ctx, cls);
        startActivity(intent);
    }
    protected void handleRegisterRenter() {
        String companyname_handle = companyname.getText().toString();
        String address_handle = address.getText().toString();
        String phonenumber_handle = phoneNumber.getText().toString();

        if (companyname_handle.isEmpty() || address_handle.isEmpty() || phonenumber_handle.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mApiService.registerRenter(LoginActivity.loggedAccount.id, companyname_handle, address_handle, phonenumber_handle).enqueue(new Callback<BaseResponse<Renter>>() {
            @Override
            public void onResponse(Call<BaseResponse<Renter>> call, Response<BaseResponse<Renter>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Renter> res = response.body();
                if(res.success) {
                    LoginActivity.loggedAccount.company = res.payload;
                    finish();
                }
                Toast.makeText(mContext, "Berhasil mendaftar renter", Toast.LENGTH_SHORT);
                moveActivity(mContext, AboutMeActivity.class);
                finish();
            }
            @Override
            public void onFailure(Call<BaseResponse<Renter>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }


}