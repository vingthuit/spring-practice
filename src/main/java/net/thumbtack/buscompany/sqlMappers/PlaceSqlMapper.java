package net.thumbtack.buscompany.sqlMappers;

import net.thumbtack.buscompany.models.BusPlace;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PlaceSqlMapper {

    @Insert({
            "<script>",
            "INSERT INTO busPlace (tripDateId, place) VALUES " +
                    "<foreach item='busPlace' collection='list' open='' separator=',' close=''>" +
                    "(#{busPlace.tripDateId,jdbcType=INTEGER}, #{busPlace.place,jdbcType=INTEGER}) </foreach>",
            "</script>"})
    void insertPlaces(@Param("list") List<BusPlace> busPlaces);

    @Update("UPDATE busPlace SET passengerId = #{busPlace.passengerId} " +
            "WHERE passengerId IS NULL AND place = #{busPlace.place} AND tripDateId = #{busPlace.tripDateId}")
    boolean update(@Param("busPlace") BusPlace busPlace);

    @Update("UPDATE busPlace SET passengerId = null WHERE place = #{busPlace.place} AND tripDateId = #{busPlace.tripDateId}")
    void freePlace(@Param("busPlace") BusPlace busPlace);

    @Select("SELECT * FROM busPlace WHERE tripDateId = #{tripDateId} AND passengerId = #{passengerId}")
    BusPlace findPlace(int tripDateId, int passengerId);

    @Select("SELECT * FROM busPlace WHERE tripDateId = #{tripDateId}")
    List<BusPlace> findPlaces(@Param("tripDateId") int tripDateId);

}
