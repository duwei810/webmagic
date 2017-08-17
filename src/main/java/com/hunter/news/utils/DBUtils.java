package com.hunter.news.utils;

import java.sql.*;

/**
 * Created by duwei on 2017/8/2.
 */
public class DBUtils {
    private Connection connection = null;
    private static String url = "jdbc:mysql://localhost:3306/mydb";
    private static String user = "root";
    private static String psw = "";
    // private static String url = "jdbc:oracle:thin:@10.9.59.88:1521:ORCL";
    // private static String user = "zeromem";
    // private static String psw = "hequn@1987";

    /**
     * 获取数据库连接
     *
     * @return connection
     */
    public Connection getCon() {

        try {
            // 加载驱动
            Class.forName("com.mysql.jdbc.Driver");

            // Class.forName("oracle.jdbc.driver.OracleDriver");
            // 建立连接
            connection = DriverManager.getConnection(url, user, psw);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭数据库连接
     *
     * @param connection
     * @param statement
     * @param preparedStatement
     * @param resultSet
     * @throws SQLException
     */
    public void closeCon(Connection connection, Statement statement, PreparedStatement preparedStatement,
                         ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
            System.out.println("关闭resultset");
        }
        if (preparedStatement != null) {
            preparedStatement.close();
            System.out.println("关闭preparedStatement");
        }
        if (statement != null) {
            statement.close();
            System.out.println("关闭statement");
        }
        if (connection != null) {
            connection.close();
            System.out.println("关闭connection");
        }
    }
}
