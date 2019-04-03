/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author peng
 * 关于 com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException:
 * Data source rejected establishment of connection,  
 * message from server: "Too many connections"  报错
 * 编辑 /etc/mysql/mysql.conf.d/mysqld.cnf 文件 加入或修改为 max_connections=1000 增大连接数
 * show status like 'Threads%'; 命令是查看当前连接状态的
 */
public class Database {
//    static DB_connection db;
    static Connection conn;
    static DB_executer exec;

//    public Database() {
//        db = new DB_connection();
//        conn = db.connect();
//        exec = new DB_executer();
//    }
    
    public static void update(String sql) throws SQLException{
        System.out.println(sql);
        try{
            conn = C3P0_connection.connect();
            exec.update(conn, sql);
        }finally{
            C3P0_connection.close(conn);
        }
    }
    public static List<Map<String, Object>> select(String sql) throws SQLException{
        System.out.println(sql);
        List<Map<String, Object>> list = null;
        try {
            conn = C3P0_connection.connect();
            list =  exec.select(conn, sql);
            System.out.println("查询成功");
        } finally{
            C3P0_connection.close(conn);
        }
        return list;
    }
    
//    public void close() throws SQLException{
////        conn.close();
//        db.close();
//    }
}
