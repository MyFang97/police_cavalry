package com.example.police.models;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;

public class DbUtility {

    private static PeopleInfo people;
    private static Connection conn = null; // ?????????????????????????????
    private static PreparedStatement stmt = null; // ?? Connection ?????????????SQL???
    private static ResultSet rs = null; // ?????Statement?????????????????

    public DbUtility() throws ClassNotFoundException, SQLException {
        Log.d("Dbutility", "创建数据库连接");
        Class.forName("com.mysql.cj.jdbc.Driver");
//        Class.forName("com.mysql.jdbc.Driver");
        Log.d("DbUtility", "加载驱动成功");
        String url = "jdbc:mysql://42.192.164.133:3306/police?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String password = "123456";
        try {
            Log.w("DbUtility", "初始化conn");
            conn = DriverManager.getConnection(url, user, password);
            Log.w("DbUtility", "初始化conn成功");
        } catch (Exception e) {
            Log.w("DbUtility", "初始化conn失败" + e);
            e.printStackTrace();
        }
        Log.d("Dbutility", "-->conn:" + conn);
    }

    public static PeopleInfo getPeopleInfoByUserName(String userName)
            throws SQLException {
        String sql;
        sql = "SELECT * FROM user_info where user_name=?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            System.out.println("???" + stmt.toString());
            Log.d("DbUtility", "stmt:" + stmt);
            rs = stmt.executeQuery();
            if (rs.next()) {
                people = new PeopleInfo(rs.getInt("id"), userName,
                        rs.getString("user_passwd"), rs.getInt("user_status"));
                System.out.println(
                        "?????:" + people.getId() + "-->" + people.getStatus());
            } else {
                System.out.println("???????");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ?????????????
        return people;
    }

    public static void release() {
        release(stmt);
        release(conn);
    }

    private static <T extends AutoCloseable> void release(T smt) {
        if (smt != null) {
            try {
                smt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            smt = null;
        }
    }

    public static void release(PreparedStatement smt, Connection conn,
                               ResultSet rs) {
        release(smt);
        release(conn);
        release(rs);
    }

}