package com.nhom6.shopn6.adapter;

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

import com.nhom6.shopn6.R;
import com.nhom6.shopn6.model.sanpham;

import java.util.List;

public class adaptersanpham extends RecyclerView.Adapter<adaptersanpham.ViewHolder> {
    private List<sanpham> listsp;

    public adaptersanpham(List<sanpham> listsp) {
        this.listsp = listsp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_tc,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getAnhsp().setImageResource(listsp.get(position).getAnhSP());
        holder.getTensp().setText(listsp.get(position).getTenSP());
        holder.getGiasp().setText(listsp.get(position).getGiasp()+"");
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
        private final Button buy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            anhsp = itemView.findViewById(R.id.imageView);
            tensp = itemView.findViewById(R.id.textView);
            giasp = itemView.findViewById(R.id.textView2);
            buy = itemView.findViewById(R.id.button);

            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "Đã mua sản phẩm: "+tensp.getText().toString(), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < listsp.size(); i++) {
                        if(listsp.get(i).getTenSP().equals(tensp.getText().toString())){
                            listsp.remove(i);
                            notifyDataSetChanged();
                            break;
                        }
                    }
                }
            });
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
