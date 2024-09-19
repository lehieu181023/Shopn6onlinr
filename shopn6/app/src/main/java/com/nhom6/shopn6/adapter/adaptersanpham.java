package com.nhom6.shopn6.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.nhom6.shopn6.Interface.ItemClickListener;
import com.nhom6.shopn6.R;
import com.nhom6.shopn6.manhinh.MainActivity_chi_tiet;
import com.nhom6.shopn6.model.sanpham;

import java.text.DecimalFormat;
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
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        holder.getGiasp().setText("Giá "+ decimalFormat.format(listsp.get(position).getGiasp()) + " VND");
        Log.d("halo","position :"+position );
        holder.setItemOnClicklistener(new ItemClickListener() {
            @Override
            public void onclick(View view, int pos, boolean isLongClick) {
                if (isLongClick == false){
                    Intent intent = new Intent(context.getApplicationContext(), MainActivity_chi_tiet.class);
                    intent.putExtra("chitiet",listsp.get(pos));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return listsp.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView anhsp;
        private final TextView tensp;
        private final TextView giasp;
        private ItemClickListener itemOnClicklistener;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            anhsp = itemView.findViewById(R.id.imageView);
            tensp = itemView.findViewById(R.id.textView);
            giasp = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(this);
        }
        public void setItemOnClicklistener(ItemClickListener itemOnClicklistener) {
            this.itemOnClicklistener = itemOnClicklistener;
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

        @Override
        public void onClick(View view) {
            itemOnClicklistener.onclick(view, getAdapterPosition(), false);
        }
    }
}
