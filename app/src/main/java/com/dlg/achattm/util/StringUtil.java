package com.dlg.achattm.util;

import java.util.Calendar;

/**
 * author: crzep
 * create time: 2020/7/15
 * description:
 * Version: 1.0
 **/
public class StringUtil {

    /**
     * 判断注册码是否是19位只包含数字和字母
     * SHHC-1234-TEST-CODE
     * @param code 注册码
     * @return true 是 false 否
     */
    public static boolean registrationCodeCheck(String code){
        if (code==null || code.length()!=19)return false;
        String co=code.replace("-","");
        if (co.length()!=16)return false;
        for(int i=co.length();--i>=0;){
            int chr=co.charAt(i);
            if(!(Character.isDigit(chr) || Character.isLetter(chr)))
                return false;
        }
        return true;
    }

    /**
     * 日历转String
     * @param cal 传入日历对象
     * @return 返回2020-09-09
     */
    public static String calenderToString(Calendar cal){
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.MONTH);
        return year+"-"+month+"-"+day;
    }

    /**
     * 从URL获取文件名
     * @param url
     * @return
     */
    public static String getFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

}
