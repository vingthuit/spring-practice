package net.thumbtack.buscompany.sqlMappers;

import net.thumbtack.buscompany.models.Client;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ClientSqlMapper {
    @Insert("INSERT INTO client (userId, firstName, lastName, patronymic, email, phone, password, salt) " +
            "VALUES (#{client.userId}, #{client.firstName}, #{client.lastName}, " +
            "#{client.patronymic}, #{client.email}, #{client.phone}, #{client.password}, #{client.salt})")
    void insert(@Param("client") Client client);

    @Select("SELECT * FROM client WHERE userId = #{id}")
    Client findByKey(int id);

    @Update("UPDATE client SET firstName = #{client.firstName}, lastName = #{client.lastName}," +
            "patronymic = #{client.patronymic}, email = #{client.email}, phone = #{client.phone}," +
            "password = #{client.password} WHERE userId = #{client.userId}")
    void update(@Param("client") Client client);

    @Select("SELECT * FROM client")
    List<Client> findAll();

    @Delete("DELETE FROM client WHERE userId = #{id}")
    void delete(int id);

}
