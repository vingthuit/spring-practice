package net.thumbtack.buscompany.sqlMappers;

import net.thumbtack.buscompany.models.User;
import org.apache.ibatis.annotations.*;

public interface UserSqlMapper {

    @Insert("INSERT INTO user (login, userType) VALUES (#{login}, #{user.userType})")
    @Options(useGeneratedKeys = true, keyProperty = "user.userId")
    void insertUser(String login, @Param("user") User user);

    @Select("SELECT * FROM user WHERE login = #{login}")
    User findByKey(String login);

    @Select("SELECT * FROM user WHERE userId = #{userId}")
    User findById(int userId);

}
