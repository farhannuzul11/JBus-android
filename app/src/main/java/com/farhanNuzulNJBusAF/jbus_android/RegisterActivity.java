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
import com.farhanNuzulNJBusAF.jbus_android.request.BaseApiService;
import com.farhanNuzulNJBusAF.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity for user registration.
 *
 * <p>
 * This activity allows users to register a new account by providing a name, email, and password.
 * The user's information is sent to the server using Retrofit. If the registration is successful,
 * the user is redirected to the login screen.
 * </p>
 *
 * @see LoginActivity
 */
public class RegisterActivity extends AppCompatActivity {

    private BaseApiService mApiService;
    private Context mContext;
    private EditText name, email, password;
    private Button registerButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        name = findViewById(R.id.editTextText);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        registerButton = findViewById(R.id.register_now);

        registerButton.setOnClickListener(v -> {
            handleRegister();
        });
    }

    /**
     * Handles the registration process.
     *
     * <p>
     * Retrieves user input for name, email, and password. Sends a registration request to the server
     * using Retrofit. Displays a toast message based on the server's response. If registration is
     * successful, the user is redirected to the login screen.
     * </p>
     */
    protected void handleRegister() {
        String nameS = name.getText().toString();
        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();

        if (nameS.isEmpty() || emailS.isEmpty() || passwordS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mApiService.register(nameS, emailS, passwordS).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Account> res = response.body();

                if(res.success) finish();
                Toast.makeText(mContext,res.message,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
        moveActivity(this, LoginActivity.class);
    }

    /**
     * Navigates to the specified activity.
     *
     * @param ctx The context from which the intent is created.
     * @param cls The class of the activity to navigate to.
     */
    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent (ctx, cls);
        startActivity(intent);
    }

}