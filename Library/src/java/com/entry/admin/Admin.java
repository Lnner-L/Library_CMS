/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entry.admin;

import com.database.Database;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author peng
 */
public class Admin {
    public static String show() throws SQLException{
        String json = null;
        String sql = "select id,username from admin";
        List<Map<String,Object>> list = Database.select(sql);
        Gson gson = new Gson();
        json = gson.toJson(list);
        return json;
    }
    
    public static String edit(Map<String,String[]> map) throws SQLException{
        for(Map.Entry e : map.entrySet()){
            System.out.println(e.getKey());
            String[] s = (String[]) e.getValue();
            for(int i=0;i<s.length;i++){
                System.out.println(s[i]);
            }
        }
        String id = map.get("id")[0];
        String newname = map.get("newname")[0];
        String newpwd = map.get("newpwd")[0];
        String checkSql = String.format("select * from admin where username='%s' and id!='%s';", 
                newname,id);
        List l = Database.select(checkSql);
        System.out.println("ddsdf  "+l);
        if(!Database.select(checkSql).isEmpty()){
//            System.out.println(new String("{'res':'false','return':'用户名存在'}").replace("'", "\""));
            return new String("{'res':'false','return':'用户名存在'}").replace("'", "\"");
        }
        String sql = String.format("update admin set username='%s' , password='%s' where id='%s'; ",
                newname, newpwd, id);
        Database.update(sql);
        return "{'res':'true','return':'编辑成功'}".replace("'", "\"");
//        String checkUsername = "select * from admin where username="+
    }
}
