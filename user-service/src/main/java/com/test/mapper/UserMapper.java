package com.test.mapper;

import com.test.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from db_user where uid = #{uid}")
    User getUserById(String uid);

    @Insert("insert into db_user values (#{uid}, #{pwd}, false)")
    void register(String uid, String pwd);
}
