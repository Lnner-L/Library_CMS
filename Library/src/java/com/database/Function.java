/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.database;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author peng
 */
public class Function {
    static public List<Object> getObjectList(String claz_str, List<Map<String, Object> > list){
        Class clazz = null;
        try {
            clazz = Class.forName(claz_str);
        }catch(Exception e) {e.printStackTrace(); }
        return getObjectList(clazz, list);
    }    
    
    static public List<Object> getObjectList(Class clazz, List<Map<String, Object> > list){
        //创建结果的对象数组
        List<Object> result = new ArrayList<Object>();
        Method[] methods = clazz.getMethods();
        Map<String, Method> map = new HashMap<>();
        for(Method m:methods){
            map.put(m.getName(), m);
        }
        
        try {
//            Method[] methods = clazz.getMethods();
            for(Map<String, Object> m:list){ //每个循环中就只相当于处理一个map
                //每一个循环(list中元素)(数据库表的一行)都要创建一个实例
                Object obj = clazz.newInstance();
                for (Entry<String, Object> entry: m.entrySet()) {
                    //我得到每一个key 和value
                    String key = entry.getKey();  // 就是类中变量名,数据库表头
                    Object value = entry.getValue();
                    //得到set函数
                    String method_name = "set"+key.substring(0,1).toUpperCase()+key.substring(1);
//                    Method method = clazz.getMethod(method_name, Object.class);
                    Method method = map.get(method_name);
                    //执行set函数,进行对象赋值
                    method.invoke(obj, value);
                }
                //放入结果数组
                result.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    } 
    
    
    
}
