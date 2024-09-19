package com.nhom6.shopn6.manhinh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.nhom6.shopn6.R;
import com.nhom6.shopn6.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DangKyActivity extends AppCompatActivity {
    TextInputEditText intxtsdt,intxtemail,intxtpass,intxtpassag;
    AppCompatButton btndk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();
        ActionDangky();
    }

    private void ActionDangky() {
        btndk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sdt = intxtsdt.getText().toString();
                String email = intxtemail.getText().toString();
                String pass = intxtpass.getText().toString();
                String passag = intxtpassag.getText().toString();
                if (passag.equals(pass)){
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(() -> {
                        User user = new User(sdt,email,pass);
                        if (user.themUser() == 0){
                            runOnUiThread(() -> {
                                Toast.makeText(DangKyActivity.this, "Đang ký thành công", Toast.LENGTH_SHORT).show();
                                runOnUiThread(() -> {
                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                    Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
                                    startActivity(intent);
                                });
                            });
                        } else if (user.themUser() == 1) {
                            runOnUiThread(() -> {
                                Toast.makeText(DangKyActivity.this, "tài khoản hoac Email đã tồn tại", Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            runOnUiThread(() -> {
                                Toast.makeText(DangKyActivity.this, "Lỗi,Hãy kiểm tra internet của bạn hoặc liên hệ lại với nhà phát triển", Toast.LENGTH_SHORT).show();
                            });
                        }
                    });

                }
                else {
                    Toast.makeText(DangKyActivity.this, "vui lòng nhập mât khẩu giống nhau", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Anhxa() {
        intxtemail = findViewById(R.id.intxtemaildk);
        intxtpass = findViewById(R.id.intxtpassdk);
        intxtsdt = findViewById(R.id.intxtsdtdk);
        intxtpassag = findViewById(R.id.intxtpassagdk);
        btndk = findViewById(R.id.btndangky);
    }
}