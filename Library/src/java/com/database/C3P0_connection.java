/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author peng
 */
public class C3P0_connection {
    //空参，自动到classpath目录下面加载“c3p0-config.xml”配置文件---配置文件的存储位置和名称必须是这样，且使用“默认配置” 
    //还有就是,连接池对象只有一个,之前将其写在connect方法中,于是每一次连接就新建了一个连接池
    static ComboPooledDataSource pooled = new ComboPooledDataSource();
    
    public static Connection connect(){
        Connection connection  = null;
//        DataSource pooled;
        //ComboPooledDataSource pool = new ComboPooledDataSource("demo");
        //加载“c3p0-config.xml”文件中定义的“demo”这个配置元素
        try{
//            pooled = new ComboPooledDataSource();
            System.out.println("pool==="+pooled);
            connection = pooled.getConnection();
            show_tatus(pooled);
            System.out.println("数据库连接成功");
        }catch (Exception e){ 
            e.printStackTrace(); 
        }
        return connection;
    }
    
    public static void close(Connection connection){
        try {
            if(connection != null ) {
                connection.close();
                System.out.println("数据库连接池释放成功");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void show_tatus(ComboPooledDataSource ds) throws SQLException{
//        ComboPooledDataSource ds = new ComboPooledDataSource();
        System.out.println("最大连接数:"+ds.getMaxPoolSize());// 最大连接数
        System.out.println("最小连接数:"+ds.getMinPoolSize());// 最小连接数
        System.out.println("正在使用连接数:"+ds.getNumBusyConnections());// 正在使用连接数
        System.out.println("空闲连接数:"+ds.getNumIdleConnections());// 空闲连接数
        System.out.println("总连接数:"+ds.getNumConnections());// 总连接数
    }
}
