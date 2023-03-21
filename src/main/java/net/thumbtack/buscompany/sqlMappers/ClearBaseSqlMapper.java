package net.thumbtack.buscompany.sqlMappers;

import org.apache.ibatis.annotations.Delete;

public interface ClearBaseSqlMapper {

    @Delete("DELETE FROM user")
    void deleteUsers();

    @Delete("DELETE FROM trip")
    void deleteTrips();

    @Delete("DELETE FROM orderTable")
    void deleteOrders();

}
