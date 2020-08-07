package com.dlg.achattm.util;

import java.io.IOException;
import java.net.URL;

/**
 * author: crzep
 * create time: 2020/7/27
 * description: 地址判断
 * Version: 1.0
 **/
public class URLUntil {


    /**
     * 判断是否是一个有效的URL地址
     * @param url 地址
     * @return true 有效 false 无效
     */
    public static boolean ifUrl(String url){
        try {
            URL u=new URL(url);
            u.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
