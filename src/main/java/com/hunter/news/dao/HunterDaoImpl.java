package com.hunter.news.dao;

import com.hunter.news.bean.News;
import com.hunter.news.bean.Picture;
import com.hunter.news.bean.Weather;
import com.hunter.news.utils.DBUtils;
import com.hunter.news.utils.TestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by duwei on 2017/8/2.
 */
public class HunterDaoImpl implements HunterDao {
//    private static Connection connection = null;
    private PreparedStatement pstmt;
    private int flag;

    public static Connection getConnectionInstance() {
        return new DBUtils().getCon();
    }

    public boolean saveData(News news) {

        String sql = "INSERT into t_news_info (id, url, title, \"TYPE\", source, \"TIME\", img, content, city) VALUES(?,?,?,?,?,?,?,?,?)";
        // String sql = "INSERT into news_info (url, title, type, source, time, img, content, city) VALUES(?,?,?,?,?,?,?,?)";
        Connection connection = getConnectionInstance();
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, UUID.randomUUID().toString().substring(0,32));
            pstmt.setString(2, news.getUrl());
            pstmt.setString(3, news.getTitle());
            pstmt.setString(4, news.getType());
            pstmt.setString(5, news.getSource());
            pstmt.setTimestamp(6,  new java.sql.Timestamp(news.getTime().getTime()));
            pstmt.setString(7, news.getImg());
            pstmt.setString(8, news.getContent());
            pstmt.setString(9, TestUtils.city);
            flag = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                new DBUtils().closeCon(connection, null, pstmt, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (flag > 0) {
            return true;
        }
        return false;
    }

    public boolean saveWeather(Weather weather) {
        String sql = "INSERT into t_weather_info (id, city, message, \"TIME\") VALUES(?,?,?,?)";
        //String sql = "INSERT into weather_info (city, message, time) VALUES(?,?,?)";
        Connection connection = getConnectionInstance();
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, UUID.randomUUID().toString().substring(0,32));
            pstmt.setString(2, weather.getCity());
            pstmt.setString(3, weather.getMessage());
            pstmt.setDate(4, new java.sql.Date(weather.getTime().getTime()));
            flag = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                new DBUtils().closeCon(connection, null, pstmt, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (flag > 0) {
            return true;
        }
        return false;
    }

    public boolean saveWeatherImg(Picture picture) {
        String sql = "INSERT into weather_img (id, message, img) VALUES(?,?,?)";
        // String sql = "INSERT into weather_img (message,img) VALUES(?,?)";
        Connection connection = getConnectionInstance();
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, UUID.randomUUID().toString().substring(0,32));
            pstmt.setString(2, picture.getMessage());
            pstmt.setString(3, picture.getImg());
            flag = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                new DBUtils().closeCon(connection, null, pstmt, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (flag > 0) {
            return true;
        }
        return false;
    }
}
