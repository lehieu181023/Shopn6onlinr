package com.nhom6.shopn6.model;

import android.util.Log;

import com.nhom6.shopn6.database.Chucnang;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class sanpham implements Serializable {
    private int id_sanpham;
    private String tenSP;
    private String anhSP;
    private double giasp;
    private String gioithieu;
    private int loaisp;

    public sanpham(int id_sanpham, String tenSP, String anhSP, double giasp, String gioithieu, int loaisp) {
        this.id_sanpham = id_sanpham;
        this.tenSP = tenSP;
        this.anhSP = anhSP;
        this.giasp = giasp;
        this.gioithieu = gioithieu;
        this.loaisp = loaisp;
    }

    public sanpham(){}


    public sanpham(int id_sanpham, String tenSP, String anhSP, double giasp, String gioithieu) {
        this.id_sanpham = id_sanpham;
        this.tenSP = tenSP;
        this.anhSP = anhSP;
        this.giasp = giasp;
        this.gioithieu = gioithieu;
    }

    public sanpham(String tenSP, String anhSP, double giasp, String gioithieu, int loaisp) {
        this.tenSP = tenSP;
        this.anhSP = anhSP;
        this.giasp = giasp;
        this.gioithieu = gioithieu;
        this.loaisp = loaisp;
    }

    public sanpham(String tenSP, String anhSP, double giasp) {
        this.tenSP = tenSP;
        this.anhSP = anhSP;
        this.giasp = giasp;
    }

    public int getLoaisp() {
        return loaisp;
    }

    public void setLoaisp(int loaisp) {
        this.loaisp = loaisp;
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

    public String  getAnhSP() {
        return anhSP;
    }

    public void setAnhSP(String anhSP) {
        this.anhSP = anhSP;
    }

    public double getGiasp() {
        return giasp;
    }

    public void setGiasp(double giasp) {
        this.giasp = giasp;
    }

    public List<sanpham> hienthi(){
        Chucnang cn = new Chucnang();
        List<sanpham> sanphamList = new ArrayList<>();
        try {
            ResultSet rs = cn.getRS("SELECT * FROM shopn6.sanpham order by ngaynhap DESC");
            while (rs.next()){
                sanphamList.add(new sanpham(rs.getInt("id_sanpham"),rs.getString("tensp"),rs.getString("anhsp"),rs.getDouble("giasp"),rs.getString("gioi_thieu"),rs.getInt("id_loai_san_pham")));
            }
        } catch (SQLException e) {
            Log.e("ero_sql", "Lỗi SQL: ", e);
        }
        return sanphamList;
    }

    public List<sanpham> hienthidstheosp(String loaisp){
        Chucnang cn = new Chucnang();
        List<sanpham> sanphamList = new ArrayList<>();
        try {
            ResultSet rs = cn.getRS("SELECT sanpham.*\n" +
                    "    FROM sanpham, loai_san_pham\n" +
                    "    WHERE sanpham.id_loai_san_pham = loai_san_pham.id_loai_san_pham and loai_san_pham.tem_lsp = '"+loaisp+"';");
            while (rs.next()){
                sanphamList.add(new sanpham(rs.getInt("id_sanpham"),rs.getString("tensp"),rs.getString("anhsp"),rs.getDouble("giasp"),rs.getString("gioi_thieu")));
            }
        } catch (SQLException e) {
            Log.e("ero_sql", "Lỗi SQL: ", e);
        }
        return sanphamList;
    }


    public void themSP() {
        Chucnang cn = new Chucnang();
        try {
            String sql = "INSERT INTO shopn6.sanpham (tensp, anhsp, giasp, gioi_thieu, id_loai_san_pham) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement stmt = cn.getSTMT(sql);
            stmt.setString(1,this.getTenSP());
            stmt.setString(2,this.getAnhSP());
            stmt.setDouble(3,this.getGiasp());
            stmt.setString(4,this.getGioithieu());
            stmt.setInt(5,this.getLoaisp());
            int rowupdate = stmt.executeUpdate();
            Log.d("nhapsql","So dong cap nhap: "+rowupdate);
        } catch (SQLException e) {
            Log.e("ero_sql", "Lỗi SQL: ", e);
        }
    }


    public void suaSP() {
        Chucnang cn = new Chucnang();
        try {
            String sql = "UPDATE shopn6.sanpham SET tensp = ?, anhsp = ?, giasp = ?, gioi_thieu = ?, id_loai_san_pham = ? WHERE id_sanpham = ?;";
            PreparedStatement stmt = cn.getSTMT(sql);
            stmt.setString(1,this.getTenSP());
            stmt.setString(2,this.getAnhSP());
            stmt.setDouble(3,this.getGiasp());
            stmt.setString(4,this.getGioithieu());
            stmt.setInt(5,this.getLoaisp());
            stmt.setInt(6,this.getId_sanpham());
            int rowupdate = stmt.executeUpdate();
            Log.d("nhapsql","So dong cap nhap: "+rowupdate);
        } catch (SQLException e) {
            Log.e("ero_sql", "Lỗi SQL: ", e);
        }
    }


    public void xoasp() {
        Chucnang cn = new Chucnang();
        try {
            String sql = "DELETE FROM shopn6.sanpham WHERE id_sanpham = ?;";
            PreparedStatement stmt = cn.getSTMT(sql);
            stmt.setInt(1,this.getId_sanpham());
            int rowupdate = stmt.executeUpdate();
            Log.d("nhapsql","So dong cap nhap: "+rowupdate);
        } catch (SQLException e) {
            Log.e("ero_sql", "Lỗi SQL: ", e);
        }
    }
}
