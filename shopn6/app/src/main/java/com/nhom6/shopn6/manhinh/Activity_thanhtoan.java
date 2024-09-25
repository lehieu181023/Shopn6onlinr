package com.nhom6.shopn6.manhinh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.nex3z.notificationbadge.NotificationBadge;
import com.nhom6.shopn6.Interface.UI;
import com.nhom6.shopn6.R;
import com.nhom6.shopn6.model.GioHang;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Activity_thanhtoan extends AppCompatActivity {
    TextInputEditText textInputEditText;
    TextView txtsotien;
    TextView txtuser;
    TextView txtemail;
    AppCompatButton btnthanhtoan;
    Toolbar toolbar;
    LottieAnimationView lottieAnimationView;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thanhtoan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();
        ActionToolBar();
        setUp();
        ActionThanhtoan();

    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void ActionThanhtoan() {
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    runOnUiThread(() -> {
                        UI.setUIEnabled(constraintLayout,false);
                        lottieAnimationView.setVisibility(View.VISIBLE);
                        lottieAnimationView.playAnimation();
                    });
                    GioHang gh = new GioHang();
                    Double sotien = Double.parseDouble(txtsotien.getText().toString().replaceAll("[^0-9]", ""));
                    gh.thanhtoan(sotien,textInputEditText.getText().toString());
                    runOnUiThread(() -> {
                        lottieAnimationView.setVisibility(View.GONE);
                        lottieAnimationView.cancelAnimation();
                        UI.setUIEnabled(constraintLayout,true);
                        Toast.makeText(Activity_thanhtoan.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                        runOnUiThread(() -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        });
                    });
                });
            }
        });
    }

    private void setUp() {
        String tongtien = getIntent().getStringExtra("tongtien");
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String user = sharedPreferences.getString("user", "");
        String email = sharedPreferences.getString("email", "");
        txtuser.setText(user);
        txtsotien.setText(tongtien);
        txtemail.setText(email);
    }

    private void Anhxa() {
        textInputEditText = findViewById(R.id.dhdiachi);
        txtemail = findViewById(R.id.dhemail);
        txtsotien = findViewById(R.id.dhsotien);
        txtuser = findViewById(R.id.dhuser);
        btnthanhtoan = findViewById(R.id.dhbtndh);
        toolbar = findViewById(R.id.toobardh);
        lottieAnimationView = findViewById(R.id.lottie_layer_thanhtoan);
        constraintLayout = findViewById(R.id.main);

    }
}