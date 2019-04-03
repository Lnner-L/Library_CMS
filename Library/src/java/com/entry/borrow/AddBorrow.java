/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entry.borrow;

import com.database.Database;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author peng
 */
public class AddBorrow {

    public String init(Map<String,String[]> map) throws SQLException{
        String phone = map.get("phone")[0];
        String ISBN = map.get("ISBN")[0];
        String checkClient = String.format("select id from clients where phone='%s';",phone);
        String checkBook = String.format("select id from books where ISBN='%s';",ISBN);
        String checkCount;
        List<Map<String,Object>> clientList;
        List<Map<String,Object>> bookList;
        List<Map<String,Object>> countList;
        clientList = Database.select(checkClient);
        if(clientList.isEmpty()){
            return "{'res':'false','return':'用户不存在'}".replace("'", "\"");
        }
        
        bookList = Database.select(checkBook);
        if(bookList.isEmpty()){
            return "{'res':'false','return':'图书不存在'}".replace("'", "\"");
        }       
        
        int bookId = (int) bookList.get(0).get("id");
        checkCount = String.format("select now_count from stock where book_id='%d'",bookId);
        countList = Database.select(checkCount);
        if((int)countList.get(0).get("now_count") == 0){
            return "{'res':'false','return':'图书已借空'}".replace("'", "\"");
        }
        
        int clientId = (int) clientList.get(0).get("id");
        long time = new Date().getTime();

        String sql1 = String.format("insert into borrow_log(client_id,book_id,time) values('%d','%d','%d');",
                clientId,bookId,time);
        Database.update(sql1);
        int borrow_log_id = (int) Database.select(String.format("select id from borrow_log where client_id='%d' and book_id='%d' and "
                + "time='%d';",clientId,bookId,time)).get(0).get("id");
        String sql2 = String.format("insert into rent_map(client_id,book_id,borrow_log_id) values('%d','%d','%d');",
                clientId,bookId,borrow_log_id);
        String sql3 = String.format("update stock set now_count=now_count-1 where id='%d';", bookId);
        
        Database.update(sql2);
        Database.update(sql3);

        return "{'res':'true','return':'借书成功'}".replace("'", "\"");
    } 
    
}
