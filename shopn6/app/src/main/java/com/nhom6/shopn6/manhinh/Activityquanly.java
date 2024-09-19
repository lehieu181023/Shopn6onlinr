package com.nhom6.shopn6.manhinh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom6.shopn6.R;
import com.nhom6.shopn6.adapter.adaptersanphamql;
import com.nhom6.shopn6.model.sanpham;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Activityquanly extends AppCompatActivity {
    Intent intent;
    ImageView imgview;
    RecyclerView recyclerView;
    List<sanpham> sanphamList;
    adaptersanphamql adapterSP;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quanly);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();
        AcionToobar();
        ActionThsp();
        SetUp();
        Indata();
    }

    private void AcionToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void Indata() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            sanpham sp = new sanpham();
            sanphamList = sp.hienthi();
            adapterSP = new adaptersanphamql(sanphamList,getApplicationContext());
            runOnUiThread(() -> {
                recyclerView.setAdapter(adapterSP);
            });
        });
    }

    private void SetUp() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void ActionThsp() {
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Activityquanly.this,themspActivity.class);
                intent.putExtra("action","them");
                startActivity(intent);
            }
        });
    }

    private void Anhxa() {
        recyclerView = findViewById(R.id.recycleview_ql);
        imgview = findViewById(R.id.img_them);
        toolbar = findViewById(R.id.toobarqlsp);
    }
}