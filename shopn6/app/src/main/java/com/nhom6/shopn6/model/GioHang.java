package com.nhom6.shopn6.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.nhom6.shopn6.Application.MyApplication;
import com.nhom6.shopn6.database.Chucnang;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GioHang extends sanpham{
    private int idsp_in_giohang;
    private int id_sanpham;
    private int id_giohang;
    private int soluong;
    private String user;

    public GioHang(String tenSP, String anhSP, double giasp, int id_sanpham, int id_giohang, int soluong) {
        super(tenSP, anhSP, giasp);
        this.id_sanpham = id_sanpham;
        this.id_giohang = id_giohang;
        this.soluong = soluong;
    }

    public GioHang(int id_sanpham, int soluong) {
        this.id_sanpham = id_sanpham;
        this.soluong = soluong;
    }

    public GioHang() {
    }

    public int getIdsp_in_giohang() {
        return idsp_in_giohang;
    }

    public void setIdsp_in_giohang(int idsp_in_giohang) {
        this.idsp_in_giohang = idsp_in_giohang;
    }

    public int getId_sanpham() {
        return id_sanpham;
    }

    public void setId_sanpham(int id_sanpham) {
        this.id_sanpham = id_sanpham;
    }

    public int getId_giohang() {
        return id_giohang;
    }

    public void setId_giohang(int id_giohang) {
        this.id_giohang = id_giohang;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
    public boolean nhan_id_gh(){
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("user", "");
        Log.d("userkk",user);
        Chucnang cn = new Chucnang();
        String sql = "SELECT * FROM shopn6.giohang where DaDat = 0 and SDT ="+user;
        try {
            ResultSet rs = cn.getRS(sql);
            if (rs.next()){
                this.id_giohang = rs.getInt("id_giohang");
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            Log.e("ero_sql", "Lỗi SQL: ", e);
            return false;
        }
    }
    public void themspgh(){
        Chucnang cn = new Chucnang();
        try {
            if (!nhan_id_gh()){
                String sqltgh = "INSERT INTO `shopn6`.`giohang` (`SDT`) VALUES (?);";
                PreparedStatement stmt = cn.getSTMT(sqltgh);
                stmt.setString(1,user);
                int rowupdate = stmt.executeUpdate();
                Log.d("nhapsql","So dong cap nhap: "+rowupdate);
                nhan_id_gh();
            }
            ResultSet rs3 = cn.getRS("select * from sp_in_giohang where id_giohang = "+this.id_giohang+" and id_sanpham = "+this.id_sanpham);
            if (rs3.next()){
                sua_so_luong();
            }
            else {
                PreparedStatement stmtgh = cn.getSTMT("INSERT INTO `shopn6`.`sp_in_giohang` (`id_sanpham`, `id_giohang`, `so_luong`) VALUES (?, ?, ?);");
                stmtgh.setInt(1,this.id_sanpham);
                stmtgh.setInt(2,this.id_giohang);
                stmtgh.setInt(3,this.soluong);
                int rowupdate = stmtgh.executeUpdate();
                Log.d("nhapsql","so luong dong cap nhap"+rowupdate);
            }
        } catch (SQLException e) {
            Log.e("ero_sql", "Lỗi SQL: ", e);
        }
    }
    public int so_luong(){
        Chucnang cn = new Chucnang();
        if (nhan_id_gh()){
            try {
                ResultSet rs = cn.getRS("select sum(so_luong) as tong_so_luong from shopn6.sp_in_giohang where id_giohang ="+this.id_giohang);
                if (rs.next()){
                    return rs.getInt("tong_so_luong");
                }
                else {
                    return 0;
                }
            } catch (SQLException e) {
                Log.e("ero_sql", "Lỗi SQL: ", e);
                return 0;
            }
        }
        else {
            return 0;
        }

    }
    public int so_luong_sp(int id_sanpham){
        if (nhan_id_gh()){
            Chucnang cn = new Chucnang();
            try {
                ResultSet rs = cn.getRS("select * from sp_in_giohang where id_giohang = "+this.id_giohang+" and id_sanpham ="+id_sanpham);
                if (rs.next()){
                    return rs.getInt("so_luong");
                }
                else {
                    return 1;
                }
            } catch (SQLException e) {
                Log.e("ero_sql", "Lỗi SQL: ", e);
                return 1;
            }
        }else {
            return 1;
        }

    }
    public void sua_so_luong(){
        Chucnang cn = new Chucnang();
        try {
            String sql = "UPDATE shopn6.sp_in_giohang SET so_luong = ? WHERE id_giohang = ? and id_sanpham = ?;";
            PreparedStatement stmt = cn.getSTMT(sql);
            stmt.setInt(1,this.soluong);
            stmt.setInt(2,this.id_giohang);
            stmt.setInt(3,this.id_sanpham);
            int rowupdate = stmt.executeUpdate();
            Log.d("nhapsql","So dong cap nhap: "+rowupdate);
        } catch (SQLException e) {
            Log.e("ero_sql", "Lỗi SQL: ", e);
        }
    }
    public List<GioHang> hienthigh(){
        List<GioHang> lgh = new ArrayList<>();
        if (nhan_id_gh()){
            Chucnang cn = new Chucnang();
            try {
                String sql = "select sp_in_giohang.id_sanpham,id_giohang,tensp,anhsp,giasp,so_luong from shopn6.sp_in_giohang,shopn6.sanpham where id_giohang = "+this.id_giohang+" and sp_in_giohang.id_sanpham = sanpham.id_sanpham;";
                ResultSet rs = cn.getRS(sql);
                while (rs.next()){
                    lgh.add(new GioHang(rs.getString("tensp"),rs.getString("anhsp"),rs.getDouble("giasp"),rs.getInt("id_sanpham"),rs.getInt("id_giohang"),rs.getInt("so_luong")));
                }
            } catch (SQLException e) {
                Log.e("ero_sql", "Lỗi SQL: ", e);
            }
            return lgh;
        }
        else {
            return lgh;
        }
    }
    public void xoasp(){
        Chucnang cn = new Chucnang();
        try {
            String sql = "DELETE FROM shopn6.sp_in_giohang WHERE id_giohang = ? and id_sanpham = ?;";
            PreparedStatement stmt = cn.getSTMT(sql);
            stmt.setInt(1,this.id_giohang);
            stmt.setInt(2,this.id_sanpham);
            int rowupdate = stmt.executeUpdate();
            Log.d("nhapsql","So dong cap nhap: "+rowupdate);
        } catch (SQLException e) {
            Log.e("ero_sql", "Lỗi SQL: ", e);
        }
    }
    public void thanhtoan(Double tongtien,String diachi){
        if (nhan_id_gh()){
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String ngayht = currentDateTime.format(formatter);
            Chucnang cn = new Chucnang();
            try {
                String sql = "UPDATE shopn6.giohang SET DaDat = 1, NgayDat = ?, ThanhTien = ?, Diachi = ? WHERE id_giohang = ?;";
                PreparedStatement stmt = cn.getSTMT(sql);
                stmt.setString(1,ngayht);
                stmt.setDouble(2,tongtien);
                stmt.setString(3,diachi);
                stmt.setInt(4,this.id_giohang);
                int rowupdate = stmt.executeUpdate();
                Log.d("nhapsql","So dong cap nhap: "+rowupdate);
            } catch (SQLException e) {
                Log.e("ero_sql", "Lỗi SQL: ", e);
            }

        }
    }
}
