package com.nhom6.shopn6.manhinh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.nhom6.shopn6.Interface.UI;
import com.nhom6.shopn6.R;
import com.nhom6.shopn6.database.DBconnect;
import com.nhom6.shopn6.model.User;

import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DangNhapActivity extends AppCompatActivity {
    AppCompatButton btndangnhap;
    TextView txtdk;
    TextInputEditText sdt;
    TextInputEditText pass;
    LottieAnimationView lottieAnimationView;
    ConstraintLayout constraintLayout;
    Connection conn;
    DBconnect db;
    String str="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();
        if (isConnect(getApplicationContext())){
            connect();
            ActionDangky();
            ActionDangnhap();
        }
        else {
            Toast.makeText(this, "Not internet", Toast.LENGTH_SHORT).show();
        }

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
    private void connect() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            runOnUiThread(() -> {
                UI.setUIEnabled(constraintLayout,false);
                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();
            });
            try {
                db = new DBconnect();
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
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            });

        });
    }

    private void ActionDangnhap() {
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(sdt.getText().toString(),pass.getText().toString());
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    runOnUiThread(() -> {
                        UI.setUIEnabled(constraintLayout,false);
                        lottieAnimationView.setVisibility(View.VISIBLE);
                        lottieAnimationView.playAnimation();
                    });

                    if (user.checkDN()==0){
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user", user.getSDT()); // Lưu số điện thoại
                        editor.putString("email", user.getEmail());
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                    else if (user.checkDN() == 1){
                        runOnUiThread(() -> {
                            lottieAnimationView.setVisibility(View.GONE);
                            lottieAnimationView.cancelAnimation();
                            UI.setUIEnabled(constraintLayout,true);
                            Toast.makeText(DangNhapActivity.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                        });
                    }
                    else if (user.checkDN() == 2){
                        runOnUiThread(() -> {
                            lottieAnimationView.setVisibility(View.GONE);
                            lottieAnimationView.cancelAnimation();
                            UI.setUIEnabled(constraintLayout,true);
                            Toast.makeText(DangNhapActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                        });
                    }
                    else {
                        runOnUiThread(() -> {
                            lottieAnimationView.setVisibility(View.GONE);
                            lottieAnimationView.cancelAnimation();
                            UI.setUIEnabled(constraintLayout,true);
                            Toast.makeText(DangNhapActivity.this, "Lỗi login,Hãy kiểm tra internet của bạn hoặc liên hệ lại với nhà phát triển", Toast.LENGTH_SHORT).show();
                        });
                    }
                });

            }
        });
    }

    private void ActionDangky() {
        txtdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DangKyActivity.class);
                startActivity(intent);
            }
        });
    }


    private void Anhxa() {
        btndangnhap = findViewById(R.id.btndangnhap);
        txtdk = findViewById(R.id.txtdangky);
        sdt = findViewById(R.id.intxtphonenb);
        pass = findViewById(R.id.intxtpass);
        lottieAnimationView =findViewById(R.id.lottie_layer_dn);
        constraintLayout = findViewById(R.id.main);
    }
}