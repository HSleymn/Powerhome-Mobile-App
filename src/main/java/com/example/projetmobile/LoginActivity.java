    package com.example.projetmobile;

    import static android.os.SystemClock.sleep;

    import android.app.Activity;
    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.util.Log;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;
    import com.example.projetmobile.api.ApiService;
    import com.example.projetmobile.api.RetrofitClient;
    import com.example.projetmobile.models.LoginRequest;
    import com.example.projetmobile.models.LoginResponse;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class LoginActivity extends Activity {

        private EditText etEmail;
        private EditText etPassword;
        private Button btnLogin;
        private TextView tvRegister;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if(getUserId() != 0){
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }


            setContentView(R.layout.activity_login);

            etEmail = findViewById(R.id.etEmail);
            etPassword = findViewById(R.id.etPassword);
            btnLogin = findViewById(R.id.btnLogin);
            tvRegister = findViewById(R.id.tvRegister);

            btnLogin.setOnClickListener(v -> {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                loginUser(email, password);
            });

            tvRegister.setOnClickListener(v -> {
                // Naviguez vers RegisterActivity

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            });
        }

        private void loginUser(String email, String password) {
            ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
            LoginRequest loginRequest = new LoginRequest(email, password);

            apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        LoginResponse loginResponse = response.body();
                        if (loginResponse.isSuccess()) {
                            // Connexion réussie, naviguez vers HomeActivity
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            storeUserId(loginResponse.getUserID());

                            finish();
                        } else {
                            // Affichez un message d'erreur
                            Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Affichez un message d'erreur
                        Toast.makeText(LoginActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("API_ERROR", "Erreur réseau : " + t.getMessage());
                }

            });
        }
        public void storeUserId(int userId) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("userId", userId);
            editor.apply();
        }
        public int getUserId() {
            SharedPreferences sharedPreferences =getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            return sharedPreferences.getInt("userId", 0);
        }

    }
