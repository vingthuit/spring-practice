package net.thumbtack.buscompany.sqlMappers;

import net.thumbtack.buscompany.models.SessionAttributes;
import org.apache.ibatis.annotations.*;

public interface SessionSqlMapper {

    @Insert("INSERT INTO sessionAttributes (id, userId) VALUES (#{session.id}, #{session.userId})")
    void insert(@Param("session") SessionAttributes session);

    @Select("SELECT * FROM sessionAttributes WHERE id = #{id}")
    SessionAttributes findByKey(String id);

    @Delete("DELETE FROM sessionAttributes WHERE id = #{id}")
    void delete(String id);

    @Update("UPDATE sessionAttributes SET password = #{password} WHERE id = #{id}")
    void update(String id, String password);

}
