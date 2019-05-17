package com.xiaoxiong.common.mybatis;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


public interface BaseDao {

    /**
     * @InsertProvider 这个注解的作用， 可以自定义sql语句
     * */
    @InsertProvider(type = BaseProvider.class,method = "save")
    public void save(@Param("obj") Object obj, @Param("table") String table);


}
