package com.nhom6.shopn6.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nhom6.shopn6.R;
import com.nhom6.shopn6.model.loai_sp;

import java.util.List;

public class adapterloaisp extends BaseAdapter {
    private List<loai_sp> menuSpList;
    private Context context;
    private int layout;

    public adapterloaisp(List<loai_sp> menuSpList, Context context, int layout) {
        this.menuSpList = menuSpList;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return menuSpList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            viewHolder.imageView = view.findViewById(R.id.im_item_menu);
            viewHolder.textView = view.findViewById(R.id.txt_tensp_menu);

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        loai_sp menuSp = menuSpList.get(i);
        viewHolder.textView.setText(menuSp.getLoai_sp());
        Glide.with(context.getApplicationContext()).load(menuSp.getIcon()).into(viewHolder.imageView);
        viewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        return view;
    }
}
