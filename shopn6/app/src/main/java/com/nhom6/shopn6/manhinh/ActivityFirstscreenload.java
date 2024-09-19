package com.nhom6.shopn6.manhinh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nhom6.shopn6.R;

public class ActivityFirstscreenload extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_firstscreenload);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userPhone = sharedPreferences.getString("user", "");
        Thread thread = new Thread(){
            public void run() {
                try {
                    sleep(4500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (!userPhone.isEmpty()){
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
                        finish();
                    }

                }
            }
        };
        thread.start();
    }
}