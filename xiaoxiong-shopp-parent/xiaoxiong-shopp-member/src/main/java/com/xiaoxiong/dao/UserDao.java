package com.xiaoxiong.dao;

import com.xiaoxiong.common.mybatis.BaseDao;
import com.xiaoxiong.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserDao extends BaseDao {

    @Select("select ID,USERNAME,PASSWORD,phone,EMAIL, created,updated from mb_user  WHERE userName=#{userName} and password=#{password}")
    public UserEntity getUserNameAndPwd(@Param("userName") String userName, @Param("password") String password);

    @Select("select ID,USERNAME,PASSWORD,phone,EMAIL, created,updated from mb_user where id =#{id} ")
    public UserEntity getUserEntity(@Param("id") long id);
}
