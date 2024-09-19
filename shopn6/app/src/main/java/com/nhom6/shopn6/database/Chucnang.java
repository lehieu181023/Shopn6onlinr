package com.nhom6.shopn6.database;



import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Chucnang {
    private Connection conn;
    public ResultSet getRS(String sql){
        DBconnect db = new DBconnect();
        ResultSet rs = null;
        try {
            conn = db.getconnect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
        }catch (SQLException e){
            Log.e("ero_sql", "Lỗi SQL: ", e);
        }
        return rs;
    }
    public PreparedStatement getSTMT(String sql){
        DBconnect db = new DBconnect();
        PreparedStatement stmt = null;
        try {
            conn = db.getconnect();
            stmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            Log.e("ero_sql", "Lỗi SQL: ", e);
        }
        return  stmt;
    }

}
