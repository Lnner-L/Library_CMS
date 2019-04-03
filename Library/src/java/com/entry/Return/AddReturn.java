/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entry.Return;

import com.database.Database;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author peng
 */
public class AddReturn {
    
public String init(Map<String,String[]> map) throws SQLException{
        String phone = map.get("phone")[0];
        String ISBN = map.get("ISBN")[0];
        String checkClient = String.format("select id from clients where phone='%s';",phone);
        String checkBook = String.format("select id from books where ISBN='%s';",ISBN);
        String checkMap;
        List<Map<String,Object>> clientList;
        List<Map<String,Object>> bookList;
        List<Map<String,Object>> mapList;
        clientList = Database.select(checkClient);
        if(clientList.isEmpty()){
            return "{'res':'false','return':'用户不存在'}".replace("'", "\"");
        }
        
        bookList = Database.select(checkBook);
        if(bookList.isEmpty()){
            return "{'res':'false','return':'图书不存在'}".replace("'", "\"");
        }       
        
        int bookId = (int) bookList.get(0).get("id");
        int clientId = (int) clientList.get(0).get("id");
        checkMap = String.format("select * from rent_map where client_id='%d' and book_id='%d';",clientId,bookId);
        mapList = Database.select(checkMap);
        if(mapList.isEmpty()){
            return "{'res':'false','return':'此用户未借此书'}".replace("'", "\"");
        }   
        
        long time = new Date().getTime();

        String sql1 = String.format("insert into return_log(client_id,book_id,time) values('%d','%d','%d');",
                clientId,bookId,time);
        String sql2 = String.format("UPDATE stock SET now_count=now_count+1 WHERE id='%d';", bookId);
        String sql3 = String.format("DELETE FROM rent_map WHERE id IN (\n" +
                                    "	SELECT t.id FROM (\n" +
                                    "       SELECT MIN(id) AS id FROM rent_map  WHERE " +
                                    "       client_id='%d' AND book_id='%d'\n" +
                                    "       ) t\n" +
                                    "	);",clientId, bookId);
        
        Database.update(sql1);
        Database.update(sql2);
        Database.update(sql3);

        return "{'res':'true','return':'还书成功'}".replace("'", "\"");
    } 
}
