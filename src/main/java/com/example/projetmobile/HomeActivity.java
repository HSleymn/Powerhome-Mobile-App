package com.example.projetmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.projetmobile.api.ApiService;
import com.example.projetmobile.api.RetrofitClient;
import com.example.projetmobile.models.LoginRequest;
import com.example.projetmobile.models.LoginResponse;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.widget.Toolbar;
public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.nav_habitats) {
                    selectedFragment = new ListHabitatsFragment();
                } else if (itemId == R.id.nav_my_habitat) {
                    selectedFragment = new MyHabitatFragment(getUserId());
                } else if (itemId == R.id.nav_notifications) {
                    selectedFragment = new NotificationsFragment();
                } else if (itemId == R.id.nav_preferences) {
                    selectedFragment = new PreferencesFragment();
                } else if (itemId == R.id.nav_about) {
                    selectedFragment = new AboutDialogFragment();
                } else if(itemId == R.id.log_out){
                    storeUserId(0);
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class ));
                    finish();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        // Default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MyHabitatFragment(getUserId()))
                    .commit();
            navigationView.setCheckedItem(R.id.nav_my_habitat);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
