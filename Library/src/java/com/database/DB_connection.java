/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author peng
 */


public class DB_connection {
    // JDBC 驱动名及数据库 URL
    String JDBC_DRIVER;// = "com.mysql.jdbc.Driver";  
    String DB_URL;// = "jdbc:mysql://localhost:3306/promotion_website?useUnicode=true&characterEncoding=UTF-8";
//    String FILENAME = "promotion/WEB_INF/classes/database.properties";
    String FILENAME = "database.properties";
    

    // 数据库的用户名与密码，需要根据自己的设置
    String USER;// = "root";
    String PASS;// = "mysql";
    Connection conn = null;

    public DB_connection() {
        try {
            Properties prop = new Properties();
            String FILE_PATH =  this.getClass().getClassLoader().getResource(FILENAME).getPath();
            System.out.println("path  "+FILE_PATH);
            File file = new File(FILE_PATH);
            FileInputStream in = new FileInputStream(file);
            prop.load(in);
            JDBC_DRIVER = prop.getProperty("JDBC_DRIVER");
            DB_URL = prop.getProperty("DB_URL");
            USER = prop.getProperty("USER");
            PASS = prop.getProperty("PASS");
                    
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DB_connection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DB_connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
    
//    private static DB_connection dB_connection =null;
//    public static DB_connection getInstance(){
//        if(DB_connection == null){
//           DB_connection = new DB_connection;
//        }
//        return  DB_connection;
//    }
    public Connection connect(){
        try{
            Class.forName(JDBC_DRIVER);
            
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            if(!conn.isClosed())
                System.out.println("Succeeded connecting to the Database!");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }
        
        
    public void close(){
        try{
            // 完成后关闭
            conn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
