package ru.wedro22.database;

import java.sql.*;

public class DBSqlite {
    private static Connection conn = null;
    private static final String DB_DRIVER="jdbc:sqlite:";
    private static String DB_SUMMARY;

    public static Connection getConnection(){
        return conn;
    }

    /**
     * @param filename separator = "/"
     */
    public static boolean connect(String filename){
        DB_SUMMARY=DB_DRIVER+filename;
        conn=null;
        try {
            conn = DriverManager.getConnection(DB_SUMMARY);
            System.out.println("Connect: "+DB_SUMMARY);
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println(e.getMessage());
            System.err.println("Error connect: "+DB_SUMMARY);
            return false;
        }
        return true;
    }

    public static void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
            System.out.println("Disconnect: "+DB_SUMMARY);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * сырой запрос к БД
     * @return null при неудаче
     */
    public static ResultSet getRawQuerry(String sql){
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            System.out.println("Ошибка sql: "+sql);
            e.printStackTrace();
        }
        return null;
    }


}
