package com.xiaoxiong.common.mybatis;

import com.xiaoxiong.common.utils.ReflectionUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class BaseProvider {

    /**
     * 自定义封装sql语句
     * @return
     */
    public String save(Map<String,Object> map) {
        //表名
        final String table =(String) map.get("table");
        //实体类
        final Object obj = map.get("obj");


        String sql = new SQL() {

            {
                INSERT_INTO(table);
                VALUES(ReflectionUtils.fatherAndSonField(obj), ReflectionUtils.fatherAndSonFieldValue(obj));
            }
        }.toString();
        System.out.println(sql);
        return sql;
    }
}
