package com.nhom6.shopn6.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.nhom6.shopn6.Interface.ImageClickListenner;
import com.nhom6.shopn6.Interface.ItemClickListener;
import com.nhom6.shopn6.Interface.UI;
import com.nhom6.shopn6.R;
import com.nhom6.shopn6.manhinh.GioHangActivity;
import com.nhom6.shopn6.manhinh.MainActivity_chi_tiet;
import com.nhom6.shopn6.model.GioHang;


import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHanglist;
    TextView textView;
    LottieAnimationView lottieAnimationView;
    ConstraintLayout constraintLayout;

    public GioHangAdapter(Context context, List<GioHang> gioHanglist, TextView textView, LottieAnimationView lottieAnimationView, ConstraintLayout constraintLayout) {
        this.context = context;
        this.gioHanglist = gioHanglist;
        this.textView = textView;
        this.lottieAnimationView = lottieAnimationView;
        this.constraintLayout = constraintLayout;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context.getApplicationContext()).load(gioHanglist.get(position).getAnhSP()).into(holder.anhsp);
        holder.anhsp.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.soluongsp.setText(gioHanglist.get(position).getSoluong()+"");
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        holder.giasp.setText(decimalFormat.format(gioHanglist.get(position).getGiasp())+" VND");
        holder.tensp.setText(gioHanglist.get(position).getTenSP());
        holder.setItemOnClicklistener(new ImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                int sl = Integer.parseInt(holder.soluongsp.getText().toString());
                if (giatri == 1){
                    sl = sl +1;
                    final int ssl = sl;
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(() -> {
                        boolean handler1 = new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                UI.setUIEnabled(constraintLayout,false);
                                lottieAnimationView.setVisibility(View.VISIBLE);
                                lottieAnimationView.playAnimation();
                            }
                        });
                        gioHanglist.get(pos).setSoluong(ssl);
                        gioHanglist.get(pos).sua_so_luong();
                        boolean handler = new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                lottieAnimationView.setVisibility(View.GONE);
                                lottieAnimationView.cancelAnimation();
                                UI.setUIEnabled(constraintLayout,true);
                                holder.soluongsp.setText(""+ssl);
                                textView.setText(tinhtien());
                            }
                        });
                    });
                }else {
                    sl = sl -1;
                    if (sl == 0){
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.execute(() -> {
                            boolean handler1 = new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    UI.setUIEnabled(constraintLayout,false);
                                    lottieAnimationView.setVisibility(View.VISIBLE);
                                    lottieAnimationView.playAnimation();
                                }
                            });
                            gioHanglist.get(pos).xoasp();
                            gioHanglist.remove(pos);
                            notifyItemRemoved(pos);
                            boolean handler = new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    lottieAnimationView.setVisibility(View.GONE);
                                    lottieAnimationView.cancelAnimation();
                                    UI.setUIEnabled(constraintLayout,true);
                                    textView.setText(tinhtien());
                                }
                            });
                        });
                    }
                    else {
                        final int ssl = sl;
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.execute(() -> {
                            boolean handler1 = new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    UI.setUIEnabled(constraintLayout,false);
                                    lottieAnimationView.setVisibility(View.VISIBLE);
                                    lottieAnimationView.playAnimation();
                                }
                            });
                            gioHanglist.get(pos).setSoluong(ssl);
                            gioHanglist.get(pos).sua_so_luong();
                            boolean handler = new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    lottieAnimationView.setVisibility(View.GONE);
                                    lottieAnimationView.cancelAnimation();
                                    UI.setUIEnabled(constraintLayout,true);
                                    holder.soluongsp.setText(""+ssl);
                                    textView.setText(tinhtien());
                                }
                            });
                        });
                    }
                }
            }
        });
    }
    public String tinhtien(){
        if (gioHanglist.size() != 0) {
            Double tongtien = 0.0;
            for (int i = 0; i < gioHanglist.size(); i++) {
                Double tsp =  gioHanglist.get(i).getSoluong() * gioHanglist.get(i).getGiasp();
                tongtien = tongtien + tsp;
            }
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            return decimalFormat.format(tongtien) +" VND";
        }
        else {
            return 0+ " VND";
        }
    }
    @Override
    public int getItemCount() {
        return gioHanglist.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView anhsp;
        private TextView tensp,giasp,soluongsp;
        private ImageButton btnc,btnt;
        private ImageClickListenner imageClickListenner;

        public ImageView getAnhsp() {
            return anhsp;
        }

        public TextView getTensp() {
            return tensp;
        }

        public TextView getGiasp() {
            return giasp;
        }

        public TextView getSoluongsp() {
            return soluongsp;
        }

        public ImageButton getBtnc() {
            return btnc;
        }

        public ImageButton getBtnt() {
            return btnt;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.item_giohang_tensp);
            anhsp = itemView.findViewById(R.id.item_giohang_image);
            giasp = itemView.findViewById(R.id.txtgiasp_gh);
            soluongsp = itemView.findViewById(R.id.txtsoluong_spgh);
            btnc = itemView.findViewById(R.id.btncong);
            btnt = itemView.findViewById(R.id.btntru);
            btnt.setOnClickListener(this);
            btnc.setOnClickListener(this);
        }
        public void setItemOnClicklistener(ImageClickListenner itemOnClicklistener) {
            this.imageClickListenner = itemOnClicklistener;
        }

        @Override
        public void onClick(View view) {
            if (view == btnc){
                imageClickListenner.onImageClick(view, getAdapterPosition(), 1);
                // 1 cong
            }else if (view == btnt){
                // 2 tru
                imageClickListenner.onImageClick(view, getAdapterPosition(),2);
            }
        }
    }
}
