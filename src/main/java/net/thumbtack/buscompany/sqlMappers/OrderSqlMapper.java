package net.thumbtack.buscompany.sqlMappers;

import net.thumbtack.buscompany.models.Order;
import net.thumbtack.buscompany.models.Passenger;
import net.thumbtack.buscompany.models.TripDate;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface OrderSqlMapper {
    @Insert("INSERT INTO orderTable (clientId, tripDateId, totalPrice) VALUES (#{order.clientId}, #{tripDateId}, #{order.totalPrice})")
    @Options(useGeneratedKeys = true, keyProperty = "order.id")
    void insert(@Param("order") Order order, @Param("tripDateId") int tripDateId);

    @Insert({
            "<script>",
            "INSERT INTO passenger (orderId, firstName, lastName, passport)",
            "VALUES <foreach item='passenger' collection='list' open='' separator=',' close=''>" +
                    "(#{orderId,jdbcType=INTEGER}, #{passenger.firstName,jdbcType=VARCHAR}, " +
                    "#{passenger.lastName,jdbcType=VARCHAR}, #{passenger.passport,jdbcType=VARCHAR}) </foreach>",
            "</script>"})
    @Options(useGeneratedKeys = true)
    void insertPassengers(int orderId, @Param("list") List<Passenger> passengers);

    @Select("SELECT * FROM orderTable WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "tripDate", column = "tripDateId", javaType = TripDate.class,
                    one = @One(select = "net.thumbtack.buscompany.sqlMappers.TripSqlMapper.findDate", fetchType = FetchType.LAZY)),
            @Result(property = "passengers", column = "id", javaType = List.class,
                    many = @Many(select = "findPassenger", fetchType = FetchType.LAZY))
    })
    Order findByKey(int id);

    @Select("SELECT * FROM orderTable")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "tripDate", column = "tripDateId", javaType = TripDate.class,
                    one = @One(select = "net.thumbtack.buscompany.sqlMappers.TripSqlMapper.findDate", fetchType = FetchType.LAZY)),
            @Result(property = "passengers", column = "id", javaType = List.class,
                    many = @Many(select = "findPassengers", fetchType = FetchType.LAZY))
    })
    List<Order> findAll();

    @Select("SELECT * FROM passenger WHERE orderId = #{order.id}")
    List<Passenger> findPassengers(@Param("order") Order order);

    @Select("SELECT * FROM passenger WHERE orderId = #{id}")
    List<Passenger> findPassenger(String id);

    @Delete("DELETE FROM orderTable WHERE id = #{id}")
    void delete(int id);

}
