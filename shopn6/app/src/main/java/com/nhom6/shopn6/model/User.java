package com.nhom6.shopn6.model;


import android.util.Log;

import com.nhom6.shopn6.Interface.HashPassword;
import com.nhom6.shopn6.database.Chucnang;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class User {
    private String SDT;
    private String Email;
    private String Password;

    public User() {
    }

    public User(String SDT, String password) {
        this.SDT = SDT;
        Password = password;
    }

    public User(String SDT, String email, String password) {
        this.SDT = SDT;
        Email = email;
        Password = password;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int themUser(){
        // return ero code
        // 0: not ero
        // 1: ero UNIQUE
        // 2: ero foreign keys
        // 3: ero another
        Chucnang cn = new Chucnang();
        try {
            String sql = "INSERT INTO `shopn6`.`user`\n" +
                    "(`SDT`,\n" +
                    "`Password`,\n" +
                    "`Email`)\n" +
                    "VALUES\n" +
                    "(?,\n" +
                    "?,\n" +
                    "?);";
            PreparedStatement stm = cn.getSTMT(sql);
            stm.setString(1,this.SDT);
            stm.setString(3, this.Email);
            stm.setString(2, HashPassword.hashpassword(this.Password));
            int rowupdate = stm.executeUpdate();
            Log.d("nhapsql","So dong cap nhap: "+rowupdate);
            return 0;

        }catch (SQLIntegrityConstraintViolationException e) {
            // Kiểm tra mã lỗi SQL của MySQL
            if (e.getErrorCode() == 1062) {
                // 1062 là mã lỗi khi vi phạm ràng buộc UNIQUE
                Log.d("nhapsql","tài khoản hoac Email đã tồn tại");
                return 1;
            } else {
                // Xử lý các lỗi ràng buộc khác nếu cần
                e.printStackTrace();
                return 2;
            }
        } catch (SQLException e) {
            // Xử lý các lỗi SQL khác
            e.printStackTrace();
            return 3;
        }
    }
    public int checkDN(){
        Chucnang cn = new Chucnang();
        try {
            String sql = "SELECT * FROM shopn6.user where SDT ="+this.SDT;
            ResultSet rs = cn.getRS(sql);
            if (rs.next()){
                String pass = rs.getString("Password");
                if (HashPassword.checkpass(this.Password,pass)){
                    this.Email = rs.getString("Email");
                    return 0;
                }else {
                    return 1;
                }
            }
            else {
                return 2;
            }
        } catch (Exception e) {
            Log.e("ero_sql", "Lỗi SQL: ", e);
            return 3;
        }
    }
}
