package com.nhom6.shopn6.manhinh;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.nhom6.shopn6.R;
import com.nhom6.shopn6.adapter.adaptersanpham;
import com.nhom6.shopn6.database.DBconnect;
import com.nhom6.shopn6.model.sanpham;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    ArrayList<sanpham> listsp;
    adaptersanpham adapter;
    DBconnect db ;
    Connection conn;
    String str = null;
    List<sanpham> sanphamList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();
        Actionbar();
        db = new DBconnect();
        if(isConnect(this)){
            connect();
            ActionViewFlip();
            hienthisanpham();
        }else {
            Toast.makeText(this, "Mất kết nối internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void hienthisanpham() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            sanpham sp = new sanpham();
            listsp = (ArrayList<sanpham>) sp.hienthi();
            runOnUiThread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                adapter = new adaptersanpham(listsp,getApplicationContext());
                recyclerView.setAdapter(adapter);
            });
        });

    }

    private void connect() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                conn = db.getconnect();
                if(conn == null){
                    str = "loi ket noi";
                }
                else {
                    str = "ket noi thanh cong";
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }
            runOnUiThread(() -> {
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException i){
                    i.printStackTrace();
                }
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            });

        });
    }

    private void hienthi(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            new sanpham().sua(new sanpham());
            sanphamList = new sanpham().hienthi();
            runOnUiThread(() -> {
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException i){
                    i.printStackTrace();
                }
                if (sanphamList.size() != 0){
                    Toast.makeText(MainActivity.this, sanphamList.get(0).getTenSP(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "null", Toast.LENGTH_SHORT).show();

                }

            });
        });
    }


    private boolean isConnect(Context context) {
        ConnectivityManager connectivityManager =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo moble = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected())||(moble != null && moble.isConnected())){
            return true;
        }
        else {
            return false;
        }
    }

    private void ActionViewFlip() {
        List<String> anhquangcao = new ArrayList<>();
        anhquangcao.add("https://tinypic.host/images/2024/09/08/1702744900608.jpg");
        anhquangcao.add("https://intphcm.com/data/upload/banner-tet.jpg");
        anhquangcao.add("https://innhanhsieuviet.com/wp-content/uploads/2021/04/poster-quang-cao-dep-2021.jpg");
        for (int i = 0; i<anhquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(anhquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(6000);
        viewFlipper.setAutoStart(true);
        Animation side_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.side_out);
        Animation side_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.side_in);
        viewFlipper.setInAnimation(side_in);
        viewFlipper.setOutAnimation(side_out);


    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_launcher_foreground);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        drawerLayout = findViewById(R.id.drawerlayouttc);
        toolbar = findViewById(R.id.toolbartc);
        viewFlipper = findViewById(R.id.viewflippertc);
        recyclerView = findViewById(R.id.recyclerviewtc);
        RecyclerView.LayoutManager layoutManager  = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        navigationView = findViewById(R.id.navigationviewtc);
        listView = findViewById(R.id.listviewtc);

        db = new DBconnect();
        listsp = new ArrayList<>();
    }
}