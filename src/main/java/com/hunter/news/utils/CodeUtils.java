package com.hunter.news.utils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by duwei on 2017/8/4.
 */
public class CodeUtils {

    public static String encode(String str) {
        try {
            return java.net.URLEncoder.encode(str, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decode(String str) {
        try {
            return java.net.URLDecoder.decode(str, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDate(String str) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (str.contains("小时")) {
            int num = Integer.parseInt(str.substring(0, str.indexOf('小')));
            calendar.add(Calendar.HOUR, num * (-1));
            str = sdf.format(calendar.getTime());
        } else if (str.contains("分钟")) {
            int num = Integer.parseInt(str.substring(0, str.indexOf('分')));
            calendar.add(Calendar.MINUTE, num * (-1));
            str = sdf.format(calendar.getTime());
        }
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
