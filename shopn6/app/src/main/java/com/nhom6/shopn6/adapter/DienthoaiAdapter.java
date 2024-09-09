package com.nhom6.shopn6.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nhom6.shopn6.R;
import com.nhom6.shopn6.model.SanPhamMoi;

import java.util.List;

public class DienthoaiAdapter extends RecyclerView.Adapter<DienthoaiAdapter.MyViewHolder> {
    Context context;
    List<SanPhamMoi> array;

    public DienthoaiAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dienthoai, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPhamMoi sanPham = array.get(position);
        Glide.with(context.getApplicationContext()).load(sanPham.getHinhanh()).into(holder.hinhanh);
        holder.hinhanh.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.tensp.setText(sanPham.getTensp());
        holder.giasp.setText(sanPham.getGiasp());
        holder.mota.setText(sanPham.getMota());

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tensp, giasp, mota;
        ImageView hinhanh;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.itemdt_ten);
            giasp = itemView.findViewById(R.id.itemdt_gia);
            mota = itemView.findViewById(R.id.itemdt_mota);
            hinhanh = itemView.findViewById(R.id.itemdt_image);
        }
    }
}
