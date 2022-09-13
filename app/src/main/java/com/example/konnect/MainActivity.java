package com.example.konnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.konnect.login.LoginActivity;
import com.example.konnect.ui.qr.QRGenerator;
import com.example.konnect.ui.qr.QRScanner;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.konnect.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    // Para mostrar usuario
    NavigationView mNavigationView;
    View mHeaderView;

    TextView tvCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        mAuth = FirebaseAuth.getInstance();

        // Navigation view
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation Header
        mHeaderView = mNavigationView.getHeaderView(0);

        // View
        tvCurrentUser = (TextView) mHeaderView.findViewById(R.id.textViewUser);

        // Set user
        tvCurrentUser.setText(mAuth.getCurrentUser().getEmail());

        binding.appBarMain.fabEscanearQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QRScanner.class);
                startActivity(intent);
                /*Snackbar.make(view, "Escaneando QR", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        binding.appBarMain.fabMostrarQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QRGenerator.class);
                startActivity(intent);
                /*Snackbar.make(view, "Mostrando QR", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
            logOut();
            return true;
        });
    }

    private void logOut() {
        mAuth.signOut();
        this.finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            this.finish();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }

    /*@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }*/
}