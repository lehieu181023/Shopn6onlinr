package com.nhom6.shopn6.manhinh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom6.shopn6.R;
import com.nhom6.shopn6.adapter.DienthoaiAdapter;
import com.nhom6.shopn6.model.sanpham;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class San_pham_Activity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    DienthoaiAdapter adapterDt;
    List<sanpham> sanPhamMoiList;
    Intent intentn;
    String loaisp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.san_pham_activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();
        Actionbar();
        Setuptheosp();
        Hienthilistsp();
    }

    private void Setuptheosp() {
        intentn = getIntent();
        loaisp = intentn.getStringExtra("loaisp");
        toolbar.setTitle(loaisp);
    }

    private void Hienthilistsp() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            sanpham sp = new sanpham();
            sanPhamMoiList = sp.hienthidstheosp(loaisp);
            runOnUiThread(() -> {
                adapterDt = new DienthoaiAdapter(this,sanPhamMoiList);
                recyclerView.setAdapter(adapterDt);
            });
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleview_dt);
        sanPhamMoiList = new ArrayList<>();

    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}