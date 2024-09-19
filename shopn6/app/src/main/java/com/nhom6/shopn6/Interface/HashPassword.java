package com.nhom6.shopn6.Interface;

import at.favre.lib.crypto.bcrypt.BCrypt;

public interface HashPassword {
    static String hashpassword(String password){
        return BCrypt.withDefaults().hashToString(10,password.toCharArray());
    }
    static boolean checkpass(String password,String hashpass){
        return BCrypt.verifyer().verify(password.toCharArray(), hashpass).verified;
    }
}
