package com.productexplorer.anandroidadventure.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.productexplorer.anandroidadventure.ConnectionChecker;
import com.productexplorer.anandroidadventure.R;
import com.productexplorer.anandroidadventure.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNetworkContinuously();
    }

    private void checkNetworkContinuously() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ConnectionChecker.isNetworkAvailable(SplashActivity.this)) {
                    nextActivity();
                } else {
                    binding.messageTv.setText(getString(R.string.please_turn_on_the_internet));
                    // Check again after a delay
                    handler.postDelayed(this, 1000); // Check every second
                }
            }
        }, 1000); // Start checking after 1 second
    }

    private void nextActivity() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), ProductListActivity.class));
            finish();
        }, 1500);
    }
}