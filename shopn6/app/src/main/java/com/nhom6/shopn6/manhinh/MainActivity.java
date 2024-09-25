package com.nhom6.shopn6.manhinh;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;
import com.nhom6.shopn6.R;
import com.nhom6.shopn6.adapter.adapterloaisp;
import com.nhom6.shopn6.adapter.adaptersanpham;
import com.nhom6.shopn6.database.DBconnect;
import com.nhom6.shopn6.model.GioHang;
import com.nhom6.shopn6.model.loai_sp;
import com.nhom6.shopn6.model.sanpham;
import com.nhom6.shopn6.Interface.UI;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    ImageView btngiohang;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    ArrayList<sanpham> listsp;
    adaptersanpham adapter;
    adapterloaisp adapterloaisp;
    DBconnect db ;
    Connection conn;
    String str = null;
    List<sanpham> sanphamList;
    List<loai_sp> loaiSps;
    Intent intent;
    NotificationBadge badge;
    GioHang gh;
    LottieAnimationView lottieAnimationView;
    ConstraintLayout constraintLayout;
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
            hienthimenu();
            hienthisanpham();
            Actionmenu();
            hienthigiohang();
            ActionGiohang();

        }else {
            Toast.makeText(this, "Not internet", Toast.LENGTH_SHORT).show();
        }
    }
    private void ActionGiohang() {
        btngiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void hienthigiohang() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            gh = new GioHang();
            int sl = gh.so_luong();
            runOnUiThread(() -> {
                badge.setText(""+sl);
            });
        });
    }

    private void Actionmenu() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (loaiSps.get(i).getLoai_sp()){
                    case "Điện thoại":
                        intent = new Intent(MainActivity.this,San_pham_Activity.class);
                        intent.putExtra("loaisp","Điện thoại");
                        startActivity(intent);
                        break;
                    case "Laptop":
                        intent = new Intent(MainActivity.this,San_pham_Activity.class);
                        intent.putExtra("loaisp","Laptop");
                        startActivity(intent);
                        break;
                    case "Đăng xuất":
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("user");  // Xóa dữ liệu theo khóa "user"
                        editor.remove("email");
                        editor.apply();
                        intent = new Intent(MainActivity.this,DangNhapActivity.class);
                        startActivity(intent);
                        break;
                    case "Quản lý":
                        intent = new Intent(MainActivity.this,Activityquanly.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void hienthimenu() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                loai_sp loaiSp = new loai_sp();
                loaiSps = loaiSp.hienthi();
                loaiSps.add(0,new loai_sp(101,"Trang chủ","https://icon-library.com/images/home-icon-images/home-icon-images-15.jpg"));
                loaiSps.add(new loai_sp(102,"Thông tin","https://cdn-icons-png.flaticon.com/128/665/665049.png"));
                loaiSps.add(new loai_sp(103,"Đăng xuất","https://cdn-icons-png.flaticon.com/128/1828/1828490.png"));
                loaiSps.add(new loai_sp(104,"Hỗ trợ","https://cdn-icons-png.flaticon.com/128/4961/4961759.png"));
                loaiSps.add(new loai_sp(105,"Quản lý","https://cdn-icons-png.flaticon.com/128/7656/7656409.png"));
            } catch (Exception e) {
                Log.e("ero_sql", "Lỗi SQL: ", e);
            }
            runOnUiThread(() -> {
                adapterloaisp = new adapterloaisp(loaiSps,getApplicationContext(),R.layout.item_menu);
                listView.setAdapter(adapterloaisp);
            });
        });
    }

    private void hienthisanpham() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            sanpham sp = new sanpham();
            listsp = (ArrayList<sanpham>) sp.hienthi();
            runOnUiThread(() -> {
                adapter = new adaptersanpham(listsp,getApplicationContext());
                recyclerView.setAdapter(adapter);
            });
        });

    }

    private void connect() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            runOnUiThread(() -> {
                UI.setUIEnabled(constraintLayout,false);
                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();
            });
            try {
                conn = db.getconnect();
                if(conn == null){
                    str = "Lỗi kết server";
                }
                else {
                    str = "Kết nối thành công";
                }
            }catch (Exception e){
                Log.e("ero_sql", "Lỗi SQL: ", e);
            }
            runOnUiThread(() -> {
                lottieAnimationView.setVisibility(View.GONE);
                lottieAnimationView.cancelAnimation();
                UI.setUIEnabled(constraintLayout,true);
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
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
        toolbar.setNavigationIcon(R.drawable.baseline_menu_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isConnect(getApplicationContext())){
            hienthigiohang();
        }
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
        badge = findViewById(R.id.Giohang_slct);
        btngiohang = findViewById(R.id.btngiohangct);
        lottieAnimationView = findViewById(R.id.lottie_layer_main);
        constraintLayout = findViewById(R.id.main);

        db = new DBconnect();
        listsp = new ArrayList<>();
    }

}