package com.nhom6.shopn6.model;

import com.nhom6.shopn6.database.Chucnang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class loai_sp {
    private int id_loai_sp;
    private String loai_sp;
    private String icon;

    Chucnang cn =new Chucnang();

    public loai_sp() {}

    public loai_sp(int id_menu_sp, String menu, String icon) {
        this.id_loai_sp = id_menu_sp;
        this.loai_sp = menu;
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId_loai_sp() {
        return id_loai_sp;
    }

    public void setId_loai_sp(int id_loai_sp) {
        this.id_loai_sp = id_loai_sp;
    }

    public String getLoai_sp() {
        return loai_sp;
    }

    public void setLoai_sp(String loai_sp) {
        this.loai_sp = loai_sp;
    }

    public List<loai_sp> hienthi(){
        List<loai_sp> menuSpList = new ArrayList<>();
        try {
            ResultSet rs = cn.getRS("SELECT * FROM shopn6.loai_san_pham;");
            while (rs.next()){
                menuSpList.add(new loai_sp(rs.getInt("id_loai_san_pham"),rs.getString("tem_lsp"),rs.getString("icon_sp")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return menuSpList;
    }
}
