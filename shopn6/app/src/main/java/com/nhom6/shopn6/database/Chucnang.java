package com.nhom6.shopn6.database;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Chucnang {
    private Connection conn;
    DBconnect db = new DBconnect();
    public ResultSet getRS(String sql){
        ResultSet rs = null;
        try {
            conn = db.getconnect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return rs;
    }
    public PreparedStatement getSTMT(String sql){
        PreparedStatement stmt = null;
        try {
            conn = db.getconnect();
            stmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  stmt;
    }

}
