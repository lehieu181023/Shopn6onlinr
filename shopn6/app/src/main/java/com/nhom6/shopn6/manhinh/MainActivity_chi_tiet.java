package com.nhom6.shopn6.manhinh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.nex3z.notificationbadge.NotificationBadge;
import com.nhom6.shopn6.R;
import com.nhom6.shopn6.model.GioHang;
import com.nhom6.shopn6.model.User;
import com.nhom6.shopn6.model.sanpham;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity_chi_tiet extends AppCompatActivity {
    TextView tensp,giasp,mota;
    Button btnthem;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar;
    EditText editTextslsp;
    ImageButton imageButtontsl;
    ImageButton imageButtongsl;
    sanpham sanpham;
    NotificationBadge badge;
    GioHang gh ;
    ImageView btngiohang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();
        Actionsoluong();
        ActionToolBar();
        initData();
        themsp();
        Actiongiohang();

    }

    private void Actiongiohang() {
        btngiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void themsp() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    String ssl = editTextslsp.getText().toString().trim();
                    if (ssl.isEmpty()){
                        gh = new GioHang(sanpham.getId_sanpham(),1);
                    }
                    else{
                        int sl = Integer.parseInt(ssl);
                        gh = new GioHang(sanpham.getId_sanpham(),sl);
                    }
                    gh.themspgh();
                    int tsl = gh.so_luong();
                    runOnUiThread(() -> {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        badge.setText(""+tsl);
                        Toast.makeText(MainActivity_chi_tiet.this, "Thêm thành công vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    });
                });
            }
        });
    }

    private void Actionsoluong() {
        imageButtontsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sl = editTextslsp.getText().toString().trim();
                if(sl.isEmpty()){
                    editTextslsp.setText(2+"");
                }
                else {
                    int soluong = Integer.parseInt(sl);
                    Log.d("soluong",soluong+"");
                    soluong = soluong +1;
                    editTextslsp.setText(""+soluong);
                }
            }
        });
        imageButtongsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sl = editTextslsp.getText().toString().trim();
                if(sl.isEmpty()){
                    editTextslsp.setText(1+"");
                } else if (sl.equals("1")) {
                    //do nothing
                } else {
                    int soluong = Integer.parseInt(sl);
                    soluong = soluong - 1;
                    editTextslsp.setText(""+soluong);
                }
            }
        });
    }

    private void Anhxa() {
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        btnthem = findViewById(R.id.btnthemvaogiohang);
        imghinhanh = findViewById(R.id.imgchitiet);
        toolbar = findViewById(R.id.toolbar);
        editTextslsp = findViewById(R.id.editslsp);
        imageButtongsl = findViewById(R.id.btngiamsl);
        imageButtontsl = findViewById(R.id.btntangsl);
        badge = findViewById(R.id.Giohang_sl);
        btngiohang = findViewById(R.id.btngiohang);
    }

    private void initData() {
        sanpham = (sanpham) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanpham.getTenSP());
        mota.setText(sanpham.getGioithieu().replace("•","\n•"));
        Glide.with(getApplicationContext()).load(sanpham.getAnhSP()).into(imghinhanh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá: "+decimalFormat.format(sanpham.getGiasp())+ "VND");

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            gh = new GioHang();
            int sl = gh.so_luong();
            int slsp = gh.so_luong_sp(sanpham.getId_sanpham());
            runOnUiThread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                badge.setText(""+sl);
                editTextslsp.setText(""+slsp);
            });
        });

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
}