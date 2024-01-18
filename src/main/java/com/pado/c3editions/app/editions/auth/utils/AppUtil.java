package com.pado.c3editions.app.editions.auth.utils;


public class AppUtil {

    public static String getCode(String prefix,long num){
        StringBuilder sb=new StringBuilder(prefix);
        sb.append(String.format("%06d",num));
        return sb.toString();
    }
}
