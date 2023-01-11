package com.test.mapper;

import com.test.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from db_user where uid = #{uid}")
    User getUserById(String uid);

    @Insert("insert into db_user values (#{uid}, #{pwd}, false)")
    void register(String uid, String pwd);

    @Update("update db_user set password = #{pwd} where uid = #{uid}\n")
    void modifyUserInfo(String uid, String pwd);
}
