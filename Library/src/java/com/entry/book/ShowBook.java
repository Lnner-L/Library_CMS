/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entry.book;

import com.database.Database;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author peng
 */
public class ShowBook {
    public String init() throws SQLException{
        String sql = "select books.id, ISBN, name, auther, info, sum_count, now_count from books, stock "
                + "where books.id = stock.book_id;";
        List<Map<String,Object>> list = Database.select(sql);
        Gson gson = new Gson();
        String json = gson.toJson(list);
        System.out.println(json);
        return json;
    }
}
