package com.nhom6.shopn6.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nhom6.shopn6.R;
import com.nhom6.shopn6.model.sanpham;

import java.util.List;

public class adaptersanpham extends RecyclerView.Adapter<adaptersanpham.ViewHolder> {
    private List<sanpham> listsp;
    private Context context;

    public adaptersanpham(List<sanpham> listsp, Context context) {
        this.listsp = listsp;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_tc,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context.getApplicationContext()).load(listsp.get(position).getAnhSP()).into(holder.getAnhsp());
        holder.getAnhsp().setScaleType(ImageView.ScaleType.FIT_XY);
        holder.getTensp().setText(listsp.get(position).getTenSP());
        holder.getGiasp().setText("Giá "+ listsp.get(position).getGiasp()+ " VND");
        Log.d("halo","position :"+position );
    }


    @Override
    public int getItemCount() {
        return listsp.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView anhsp;
        private final TextView tensp;
        private final TextView giasp;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            anhsp = itemView.findViewById(R.id.imageView);
            tensp = itemView.findViewById(R.id.textView);
            giasp = itemView.findViewById(R.id.textView2);

        }

        public ImageView getAnhsp() {
            return anhsp;
        }

        public TextView getTensp() {
            return tensp;
        }

        public TextView getGiasp() {
            return giasp;
        }
    }
}
