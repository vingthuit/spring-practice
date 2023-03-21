package net.thumbtack.buscompany.sqlMappers;

import net.thumbtack.buscompany.models.Admin;
import org.apache.ibatis.annotations.*;

public interface AdminSqlMapper {
    @Insert("INSERT INTO admin (userId, firstName, lastName, patronymic, position, password, salt) " +
            "VALUES (#{admin.userId}, #{admin.firstName}, #{admin.lastName}, #{admin.patronymic}, " +
            "#{admin.position}, #{admin.password}, #{admin.salt})")
    void insert(@Param("admin") Admin admin);

    @Select("SELECT * FROM admin WHERE userId = #{id}")
    Admin findByKey(int id);

    @Update("UPDATE admin SET firstName = #{admin.firstName}, lastName = #{admin.lastName}," +
            "patronymic = #{admin.patronymic}, position = #{admin.position}, " +
            "password = #{admin.password} WHERE userId = #{admin.userId}")
    void update(@Param("admin") Admin admin);

    @Select("SELECT COUNT(*) FROM admin")
    Long getCount();

    @Delete("DELETE FROM admin WHERE userId = #{id}")
    void delete(int id);

}
