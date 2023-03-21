package net.thumbtack.buscompany.sqlMappers;

import net.thumbtack.buscompany.models.Bus;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BusSqlMapper {

    @Select("SELECT * FROM bus WHERE name = #{name}")
    Bus findByKey(String name);

    @Select("SELECT * FROM bus")
    List<Bus> findAll();

}
