package com.farhanNuzulNJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.farhanNuzulNJBusAF.jbus_android.model.Account;
import com.farhanNuzulNJBusAF.jbus_android.model.BaseResponse;
import com.farhanNuzulNJBusAF.jbus_android.request.BaseApiService;
import com.farhanNuzulNJBusAF.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Activity class for handling user login.
 *
 * <p>
 * This activity allows users to log in to the JBUS application. It includes functionality for handling login requests,
 * displaying relevant messages, and navigating to the main activity upon successful login.
 * </p>
 *
 * @see androidx.appcompat.app.AppCompatActivity
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {
    private TextView registerNow = null;
    private Button login = null;
    public static Account loggedAccount;
    private Context mContext;
    private BaseApiService mApiService;
    private EditText email, password;

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerNow = findViewById(R.id.register_now);
        login = findViewById(R.id.login_n);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.login_n);

        registerNow.setOnClickListener(v -> {moveActivity(this, RegisterActivity.class);});
        login.setOnClickListener(v -> {
            handleLogin();});

    }

    /**
     * Moves to another activity.
     *
     * @param ctx The context of the current activity.
     * @param cls The class of the activity to move to.
     */
    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent (ctx, cls);
        startActivity(intent);
    }

    /**
     * Handles the login process by sending a login request to the server.
     * Displays relevant messages based on the response.
     */
    protected void handleLogin() {
        String emailNew = email.getText().toString();
        String passwordNew = password.getText().toString();

        if (emailNew.isEmpty() || passwordNew.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.login(emailNew, passwordNew).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Account> res = response.body();


                if (res.success) {
                    loggedAccount = res.payload;
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(mContext, "Welcome To JBUS " + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }


}