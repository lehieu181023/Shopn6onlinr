package com.nhom6.shopn6.manhinh;

import static com.nhom6.shopn6.Interface.ImageProcessing.copyFile;
import static com.nhom6.shopn6.Interface.ImageProcessing.getFileExtension;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.nhom6.shopn6.R;
import com.nhom6.shopn6.database.DBconnect;
import com.nhom6.shopn6.model.loai_sp;
import com.nhom6.shopn6.model.sanpham;
import com.nhom6.shopn6.uploadimage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class themspActivity extends AppCompatActivity {
    Spinner spinner;
    ImageView imgcam;
    Toolbar toolbar;
    ImageView imganhsp;
    TextInputEditText intxttenspql,intxtgiaspql,intxtmotaspql;
    File fileanh ;
    AppCompatButton btnthem;
    int loaisp;
    List<loai_sp> loaiSpList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_themsp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        Anhxa();
        ActionToolbar();
        ActionAnh();
        SetUpSpinner();
        ActionThemsp();


    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void SetUpSpinner() {
        List<String> listSpinner = new ArrayList<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            loai_sp loaiSp = new loai_sp();
            loaiSpList = loaiSp.hienthi();
            for (int i = 0; i < loaiSpList.size(); i++) {
                listSpinner.add(loaiSpList.get(i).getLoai_sp());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,listSpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            runOnUiThread(() -> {
                spinner.setAdapter(adapter);
            });
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loaisp = loaiSpList.get(i).getId_loai_sp();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                loaisp = loaiSpList.get(1).getId_loai_sp();
            }
        });
    }

    private void ActionThemsp() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBconnect db = new DBconnect();
                Double giasp = Double.parseDouble(intxtgiaspql.getText().toString()) ;
                String tensp = intxttenspql.getText().toString();
                String gioithieusp = intxtmotaspql.getText().toString();
                String anhsp = "http://"+db.getIP()+":3000/uploads/"+fileanh.getName();
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    sanpham sp = new sanpham(tensp,anhsp,giasp,gioithieusp,loaisp);
                    sp.themSP();
                    UploadTask uploadTask = new UploadTask(fileanh);
                    uploadTask.execute();
                    runOnUiThread(() -> {
                        Toast.makeText(themspActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        runOnUiThread(() -> {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            Intent intent = new Intent(getApplicationContext(),Activityquanly.class);
                            startActivity(intent);
                        });
                    });
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getData() != null) {
            Uri uri = data.getData();
            File fileImage = new File(getRealPathFromURI(uri));

            // Lấy phần mở rộng của tệp gốc
            String fileExtension = getFileExtension(fileImage);

            // Tạo tên mới cho tệp
            String newFileName ="ImageServer_ShopN6_"+ System.currentTimeMillis() + fileExtension;
            fileanh = new File(getCacheDir(), newFileName);

            // Sao chép nội dung từ tệp cũ sang tệp mới
            copyFile(fileImage, fileanh);

            // Đặt đường dẫn mới vào TextView
            imganhsp.setImageURI(uri);

        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String result;
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            result = contentUri.getPath();
        }
        else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }

    private void ActionAnh() {
        imgcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(themspActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toobartsp);
        imganhsp = findViewById(R.id.tspAnhsp);
        intxttenspql = findViewById(R.id.tsptensp);
        intxtgiaspql = findViewById(R.id.tspgiasp);
        intxtmotaspql = findViewById(R.id.tspmota);
        imgcam = findViewById(R.id.imgcamera);
        spinner = findViewById(R.id.spinner_loai);
        btnthem = findViewById(R.id.tspbtnthem);
    }
}