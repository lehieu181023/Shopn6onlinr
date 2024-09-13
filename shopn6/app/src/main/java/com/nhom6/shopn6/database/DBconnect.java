package com.nhom6.shopn6.database;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class DBconnect {
    protected final String db="shopn6";
    protected final String ip = "192.168.110.53";
    protected final String port = "3306";
    protected final String user = "nhom6";
    protected final String pass = "@Ab12345";

    //kết nối tới csdl
    public Connection getconnect(){
        Connection conn = null;
        try {
            String dbURL = "jdbc:mysql://"+ip+":"+port+"/"+db;
            conn = DriverManager.getConnection(dbURL,user,pass);
            Log.d("ketnoi","connected");
        } catch (Exception e) {
            Log.e("Erro_connect", Objects.requireNonNull(e.getMessage()));
        }
        return conn;
    }

}
