/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author peng
 */
public class DB_executer {
    
    static public void update(Connection conn, String sql) throws SQLException{
//        DB_connection db = new DB_connection();
//        Connection conn = db.connect();
        Statement stmt = conn.createStatement();
         try{
            stmt.executeUpdate(sql);
            System.out.println("execute successful");
         }
         catch(Exception e){
             e.printStackTrace();
         }
         finally{
            stmt.close();
//            db.close();
         }
    }
    
    static public List<Map<String, Object>> select(Connection conn, String sql) throws SQLException{
//        DB_connection db = new DB_connection();
//        Connection conn = db.connect();
        Statement stmt = conn.createStatement();

        // 注册 JDBC 驱动
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        ResultSet rs = null;
        try{
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            ResultSetMetaData rmd = rs.getMetaData();
            int count = rmd.getColumnCount();
             while(rs.next()){
                Map map = new HashMap(count);
                for(int i=1;i<=count;i++){
//                    System.out.print(rs.getString(i)+" ");
//                    System.out.println("");
                    map.put(rmd.getColumnName(i), rs.getObject(i));
                }
                result.add(map);
            }
            rs.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            stmt.close();
//            db.close();
        }
        return result;
    }
}
