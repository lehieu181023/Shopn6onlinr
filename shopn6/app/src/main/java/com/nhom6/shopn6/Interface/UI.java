package com.nhom6.shopn6.Interface;

import android.view.View;
import android.view.ViewGroup;

public interface UI {
    static void setUIEnabled(ViewGroup layout, boolean enabled) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                // Đệ quy để xử lý các thành phần con bên trong ViewGroup (như LinearLayout, RelativeLayout...)
                setUIEnabled((ViewGroup) child, enabled);
            } else {
                child.setEnabled(enabled); // Vô hiệu hóa hoặc kích hoạt thành phần giao diện
            }
        }
    }
}
