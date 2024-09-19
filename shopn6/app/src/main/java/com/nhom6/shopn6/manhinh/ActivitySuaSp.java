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

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.nhom6.shopn6.R;
import com.nhom6.shopn6.database.DBconnect;
import com.nhom6.shopn6.model.loai_sp;
import com.nhom6.shopn6.model.sanpham;
import com.nhom6.shopn6.uploadimage.UploadTask;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivitySuaSp extends AppCompatActivity {
    Spinner spinner;
    ImageView imgcam;
    Toolbar toolbar;
    ImageView imagesp;
    TextInputEditText intxttenspql,intxtgiaspql,intxtmotaspql;
    File fileanh ;
    AppCompatButton btnsua;
    int loaisp;
    List<loai_sp> loaiSpList;
    int idsp;
    boolean chonanh = false;
    sanpham spp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sua_sp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();
        ActionToolbar();
        ActionAnh();
        SetUpSpinner();
        indata();
        ActionSuasp();


    }

    private void indata() {
        spp = (sanpham) getIntent().getSerializableExtra("sanpham");
        intxttenspql.setText(spp.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("#");
        intxtgiaspql.setText(decimalFormat.format(spp.getGiasp()));
        intxtmotaspql.setText(spp.getGioithieu());
        Glide.with(getApplicationContext()).load(spp.getAnhSP()).into(imagesp);
        spinner.post(() -> {
            spinner.setSelection(spp.getLoaisp()-1);
        });
        idsp = spp.getId_sanpham();
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

    private void ActionSuasp() {
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBconnect db = new DBconnect();
                Double giasp = Double.parseDouble(intxtgiaspql.getText().toString()) ;
                String tensp = intxttenspql.getText().toString();
                String gioithieusp = intxtmotaspql.getText().toString();
                String anhsp;
                if (chonanh){
                    anhsp = "http://"+db.getIP()+":3000/uploads/"+fileanh.getName();
                }
                else {
                    anhsp = spp.getAnhSP();
                }
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    sanpham sp = new sanpham(idsp,tensp,anhsp,giasp,gioithieusp,loaisp);
                    sp.suaSP();
                    if(chonanh){
                        UploadTask uploadTask = new UploadTask(fileanh);
                        uploadTask.execute();
                    }
                    runOnUiThread(() -> {
                        Toast.makeText(ActivitySuaSp.this, "Sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();
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
            chonanh = true;
            Uri uri = data.getData();
            File fileImage = new File(getRealPathFromURI(uri));

            // Lấy phần mở rộng của tệp gốc
            String fileExtension = getFileExtension(fileImage);

            // Tạo tên mới cho tệp
            String newFileName ="ImageServer_ShopN6_"+ System.currentTimeMillis() + fileExtension;
            fileanh = new File(getCacheDir(), newFileName);

            // Sao chép nội dung từ tệp cũ sang tệp mới
            copyFile(fileImage, fileanh);

            imagesp.setImageURI(uri);
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
                ImagePicker.with(ActivitySuaSp.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toobartsp);
        imagesp = findViewById(R.id.sspAnhsp);
        intxttenspql = findViewById(R.id.tsptensp);
        intxtgiaspql = findViewById(R.id.tspgiasp);
        intxtmotaspql = findViewById(R.id.tspmota);
        imgcam = findViewById(R.id.imgcamera);
        spinner = findViewById(R.id.sspspinner_loai);
        btnsua = findViewById(R.id.tspbtnthem);
    }
}