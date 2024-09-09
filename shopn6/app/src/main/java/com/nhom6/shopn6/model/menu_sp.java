package com.nhom6.shopn6.model;

import com.nhom6.shopn6.database.Chucnang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class menu_sp {
    private int id_menu_sp;
    private String menu;
    Chucnang cn =new Chucnang();
    public menu_sp(int id_menu_sp, String menu) {
        this.id_menu_sp = id_menu_sp;
        this.menu = menu;
    }

    public int getId_menu_sp() {
        return id_menu_sp;
    }

    public void setId_menu_sp(int id_menu_sp) {
        this.id_menu_sp = id_menu_sp;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public List<menu_sp> hienthi(){
        List<menu_sp> menuSpList = new ArrayList<>();
        try {
            ResultSet rs = cn.getRS("SELECT * FROM shopn6.menu_sanpham;");
            while (rs.next()){
                menuSpList.add(new menu_sp(rs.getInt("id_menu_sanpham"),rs.getString("menu")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return menuSpList;
    }
}
