package net.thumbtack.buscompany.sqlMappers;

import net.thumbtack.buscompany.models.Schedule;
import net.thumbtack.buscompany.models.Trip;
import net.thumbtack.buscompany.models.TripDate;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface TripSqlMapper {

    @Insert("INSERT INTO trip (fromStation, toStation, start, duration, price, busName, approved) " +
            "VALUES (#{trip.fromStation}, #{trip.toStation}, #{trip.start}, " +
            "#{trip.duration}, #{trip.price}, #{trip.bus.name}, #{trip.approved})")
    @Options(useGeneratedKeys = true, keyProperty = "trip.id")
    void insert(@Param("trip") Trip trip);

    @Insert("INSERT INTO schedule (tripId, fromDate, toDate, tripPeriod) " +
            "values (#{tripId}, #{schedule.fromDate}, #{schedule.toDate}, #{schedule.period})")
    void insertSchedule(int tripId, @Param("schedule") Schedule schedule);

    @Insert({
            "<script>",
            "INSERT INTO tripDate (tripId, date, freePlaces) VALUES " +
                    "<foreach item='tripDate' collection='list' open='' separator=',' close=''>" +
                    "(#{tripId,jdbcType=INTEGER}, #{tripDate.date,jdbcType=DATE}, #{freePlaces,jdbcType=INTEGER}) </foreach>",
            "</script>"})
    void insertDates(int tripId, int freePlaces, @Param("list") List<TripDate> dates);

    @Select("SELECT * FROM trip WHERE trip.id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "bus", column = "busName", javaType = Schedule.class,
                    one = @One(select = "net.thumbtack.buscompany.sqlMappers.BusSqlMapper.findByKey", fetchType = FetchType.LAZY)),
            @Result(property = "schedule", column = "id", javaType = Schedule.class,
                    one = @One(select = "findSchedule", fetchType = FetchType.LAZY)),
            @Result(property = "dates", column = "id", javaType = List.class,
                    many = @Many(select = "findDates", fetchType = FetchType.LAZY))
    })
    Trip findByKey(int id);

    @Update("UPDATE trip SET fromStation = #{trip.fromStation}, toStation = #{trip.toStation}," +
            "start = #{trip.start}, duration = #{trip.duration}, price = #{trip.price}, " +
            "busName = #{trip.bus.name}, WHERE id = #{id}")
    void update(@Param("id") int id, @Param("trip") Trip trip);

    @Update("UPDATE schedule SET fromDate = #{schedule.fromDate}, toDate = #{schedule.toDate}, " +
            "tripPeriod = #{schedule.period} WHERE tripId = #{tripId}")
    void updateSchedule(@Param("tripId") int tripId, @Param("schedule") Schedule schedule);

    @Update("UPDATE tripDate SET freePlaces = freePlaces - #{placeCount} WHERE freePlaces >= #{placeCount} AND id = #{tripDate.id}")
    boolean updateTripDate(@Param("tripDate") TripDate tripDate, int placeCount);

    @Delete("DELETE * FROM schedule WHERE tripId = #{tripId}")
    void deleteSchedule(int tripId);

    @Delete("DELETE * FROM tripDate WHERE tripId = #{tripId}")
    void deleteDates(int tripId);

    @Update("UPDATE trip SET approved = 1 WHERE id = #{id}")
    void approve(@Param("id") int id);

    @Select("SELECT * FROM trip")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "bus", column = "busName", javaType = Schedule.class,
                    one = @One(select = "net.thumbtack.buscompany.sqlMappers.BusSqlMapper.findByKey", fetchType = FetchType.LAZY)),
            @Result(property = "schedule", column = "id", javaType = Schedule.class,
                    one = @One(select = "findSchedule", fetchType = FetchType.LAZY)),
            @Result(property = "dates", column = "id", javaType = List.class,
                    many = @Many(select = "findDates", fetchType = FetchType.LAZY))
    })
    List<Trip> findAll();

    @Select("SELECT * FROM schedule WHERE tripId = #{tripId}")
    Schedule findSchedule(int tripId);

    @Select("SELECT * FROM tripDate WHERE tripId = #{tripId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "tripId", column = "tripId"),
            @Result(property = "date", column = "date"),
            @Result(property = "places", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.buscompany.sqlMappers.PlaceSqlMapper.findPlaces", fetchType = FetchType.LAZY))
    })
    List<TripDate> findDates(int tripId);

    @Select("SELECT * FROM tripDate WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "tripId", column = "tripId"),
            @Result(property = "date", column = "date"),
            @Result(property = "places", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.buscompany.sqlMappers.PlaceSqlMapper.findPlaces", fetchType = FetchType.LAZY))
    })
    TripDate findDate(int id);

    @Delete("DELETE FROM trip WHERE id = #{id}")
    void delete(int id);

}
