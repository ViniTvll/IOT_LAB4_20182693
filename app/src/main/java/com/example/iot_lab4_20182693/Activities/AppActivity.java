package com.example.iot_lab4_20182693.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.iot_lab4_20182693.R;
import android.widget.Button;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // Configuración de botones
        Button btnLocation = findViewById(R.id.btnLocation);
        Button btnForecast = findViewById(R.id.btnForecast);
        Button btnFuture = findViewById(R.id.btnFuture);

        btnLocation.setOnClickListener(view ->
                navController.navigate(R.id.locationFragment)
        );

        btnForecast.setOnClickListener(view ->
                navController.navigate(R.id.forecasterFragment)
        );

        btnFuture.setOnClickListener(view ->
                navController.navigate(R.id.futureFragment)
        );
    }
}