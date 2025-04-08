// RegisterActivity.java
package com.example.projetmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.projetmobile.api.ApiService;
import com.example.projetmobile.api.RetrofitClient;
import com.example.projetmobile.models.LoginResponse;
import com.example.projetmobile.models.RegisterRequest;
import com.example.projetmobile.models.RegisterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity {

    private EditText etLastName;
    private EditText etFirstName;

    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etLastName = findViewById(R.id.lastName);

        etFirstName = findViewById(R.id.FirstName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(v -> {
            String FirstName = etFirstName.getText().toString().trim();
            String LastName = etLastName.getText().toString().trim();

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            registerUser(FirstName,LastName, email, password);
        });

        tvLogin.setOnClickListener(v -> {
            // Naviguez vers LoginActivity
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    private void registerUser(String FirstName, String LastName, String email, String password) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        RegisterRequest registerRequest = new RegisterRequest(FirstName,LastName, email, password);

        apiService.register(registerRequest).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    // Inscription réussie, naviguez vers LoginActivity
                    RegisterResponse registerResponse = response.body();

                    if (registerResponse.isSuccess()) {
                        // Connexion réussie, naviguez vers HomeActivity
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API_ERROR", "Erreur réseau : " + t.getMessage());
            }
        });
    }
}
