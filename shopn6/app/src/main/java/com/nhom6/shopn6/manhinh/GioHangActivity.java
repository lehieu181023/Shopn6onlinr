package com.nhom6.shopn6.manhinh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.nhom6.shopn6.Interface.UI;
import com.nhom6.shopn6.R;
import com.nhom6.shopn6.adapter.GioHangAdapter;
import com.nhom6.shopn6.model.GioHang;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GioHangActivity extends AppCompatActivity {
    TextView giohangtrong, tongtien;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnmuahang;
    GioHangAdapter adapter;
    List<GioHang> manggiohang;
    GioHang gh;
    LottieAnimationView lottieAnimationView;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gio_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        initControl();
        inData();
        ActionMuahang();

    }

    private void ActionMuahang() {
        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    Intent intent = new Intent(getApplicationContext(),Activity_thanhtoan.class);
                    intent.putExtra("tongtien",tongtien.getText().toString());
                    startActivity(intent);
                });
            }
        });
    }

    private void inData() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            runOnUiThread(() -> {
                UI.setUIEnabled(constraintLayout,false);
                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();
            });
            gh = new GioHang();
            manggiohang = gh.hienthigh();
            runOnUiThread(() -> {
                lottieAnimationView.setVisibility(View.GONE);
                lottieAnimationView.cancelAnimation();
                UI.setUIEnabled(constraintLayout,true);
                if (manggiohang.size() != 0){
                    adapter = new GioHangAdapter(getApplicationContext(),manggiohang,tongtien,lottieAnimationView,constraintLayout);
                    recyclerView.setAdapter(adapter);
                    tongtien.setText(adapter.tinhtien());
                }
                else{
                    tongtien.setText(0 +"VND");
                }
            });
        });
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initView() {
        giohangtrong = findViewById(R.id.txtgiohangtrong);
        tongtien = findViewById(R.id.txttongtien);
        toolbar = findViewById(R.id.toobar);
        recyclerView = findViewById(R.id.recycleviewgiohang);
        btnmuahang = findViewById(R.id.btnmuahang);
        lottieAnimationView = findViewById(R.id.lottie_layer_giohang);
        constraintLayout = findViewById(R.id.main);
    }

}