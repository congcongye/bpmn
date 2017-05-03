package com.wosai.sqb.storemap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ycc on 15/12/1.
 */
public class DBConnection {
    private static String dbDriver="com.mysql.jdbc.Driver";
    private static String dbUrl="jdbc:mysql://localhost:3306/paper?useUnicode=yes&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";//根据实际情况变化

    private static String dbUser="root";
    private static String dbPass="123456";
    static  Statement st=null;
    static Connection conn=null;
    public  static  Connection getConn()
    {
        try
        {
            Class.forName(dbDriver);
            conn = DriverManager.getConnection(dbUrl,dbUser,dbPass);//注意是三个参数
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

  public static Statement getStatement(){

        try {
             st=new DBConnection().getConn().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return st;
    }



  /*public static void main(String []args){
        Connection conn=new com.wosai.sqb.storemap.DBConnection().getConn();
        if(conn!=null){
            System.out.println("连接成功");
        }else{
            System.out.println("连接失败");
        }
    }*/

}
