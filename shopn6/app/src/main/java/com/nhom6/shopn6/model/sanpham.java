package com.nhom6.shopn6.model;

import android.util.Log;

import com.nhom6.shopn6.database.Chucnang;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class sanpham {
    private int id_sanpham;
    private String tenSP;
    private int anhSP;
    private double giasp;
    private String gioithieu;
    public sanpham(){}
    Chucnang cn = new Chucnang();

    public sanpham(int id_sanpham, String tenSP, int anhSP, double giasp, String gioithieu) {
        this.id_sanpham = id_sanpham;
        this.tenSP = tenSP;
        this.anhSP = anhSP;
        this.giasp = giasp;
        this.gioithieu = gioithieu;
    }

    public int getId_sanpham() {
        return id_sanpham;
    }

    public void setId_sanpham(int id_sanpham) {
        this.id_sanpham = id_sanpham;
    }

    public String getGioithieu() {
        return gioithieu;
    }

    public void setGioithieu(String gioithieu) {
        this.gioithieu = gioithieu;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getAnhSP() {
        return anhSP;
    }

    public void setAnhSP(int anhSP) {
        this.anhSP = anhSP;
    }

    public double getGiasp() {
        return giasp;
    }

    public void setGiasp(double giasp) {
        this.giasp = giasp;
    }

    public List<sanpham> hienthi(){
        List<sanpham> sanphamList = new ArrayList<>();
        try {
            ResultSet rs = cn.getRS("SELECT * FROM shopn6.sanpham;");
            while (rs.next()){
                sanphamList.add(new sanpham());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sanphamList;
    }


    public void them(sanpham sp) {
        try {
            String sql = "INSERT INTO `shopn6`.`sanpham`(`tensp`,`anhsp`,`giasp` ) VALUES (?,?,?)";
            PreparedStatement stmt = cn.getSTMT(sql);
            stmt.setString(1,sp.getTenSP());
            stmt.setInt(2,sp.getAnhSP());
            stmt.setDouble(3,sp.getGiasp());
            int rowupdate = stmt.executeUpdate();
            Log.d("nhapsql","So dong cap nhap: "+rowupdate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void sua(sanpham sp) {
        try {
            String sql = "UPDATE `shopn6`.`sanpham`\n" +
                    "SET\n" +
                    "`tensp` = ?,\n" +
                    "`anhsp` = ?,\n" +
                    "`giasp` = ?\n" +
                    "WHERE `id_sanpham` = ?;";
            PreparedStatement stmt = cn.getSTMT(sql);
            stmt.setString(1,sp.getTenSP());
            stmt.setInt(2,sp.getAnhSP());
            stmt.setDouble(3,sp.getGiasp());
            stmt.setInt(4,1);
            int rowupdate = stmt.executeUpdate();
            Log.d("nhapsql","So dong cap nhap: "+rowupdate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void xoa(sanpham sp) {
        try {
            String sql = "UPDATE `shopn6`.`sanpham`\n" +
                    "SET\n" +
                    "`tensp` = ?,\n" +
                    "`anhsp` = ?,\n" +
                    "`giasp` = ?\n" +
                    "WHERE `id_sanpham` = ?;";
            PreparedStatement stmt = cn.getSTMT(sql);
            stmt.setString(1,sp.getTenSP());
            stmt.setInt(2,sp.getAnhSP());
            stmt.setDouble(3,sp.getGiasp());
            stmt.setInt(4,1);
            int rowupdate = stmt.executeUpdate();
            Log.d("nhapsql","So dong cap nhap: "+rowupdate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
