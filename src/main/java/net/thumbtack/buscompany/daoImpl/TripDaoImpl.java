package net.thumbtack.buscompany.daoImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.thumbtack.buscompany.dao.TripDao;
import net.thumbtack.buscompany.models.Schedule;
import net.thumbtack.buscompany.models.TripDate;
import net.thumbtack.buscompany.security.exceptions.DatabaseException;
import net.thumbtack.buscompany.models.Trip;
import net.thumbtack.buscompany.security.exceptions.ErrorCode;
import net.thumbtack.buscompany.sqlMappers.PlaceSqlMapper;
import net.thumbtack.buscompany.sqlMappers.TripSqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class TripDaoImpl implements TripDao {
    private final SqlSessionFactory sqlSessionFactory;

    @Override
    public void insert(Trip trip) {
        log.debug("DAO insert Trip {}", trip);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                TripSqlMapper mapper = sqlSession.getMapper(TripSqlMapper.class);
                mapper.insert(trip);
                insertDates(trip, mapper);
                insertPlaces(sqlSession, trip);
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.INSERT_FAILED;
                log.debug(errorCode.getMessage(), ex);
                sqlSession.rollback();
                throw new DatabaseException(errorCode);
            }
            sqlSession.commit();
        }
    }

    private void insertDates(Trip trip, TripSqlMapper mapper) {
        int tripId = trip.getId();
        if (trip.getSchedule() != null) {
            mapper.insertSchedule(tripId, trip.getSchedule());
        }
        int freePlaces = trip.getBus().getPlaceCount();
        mapper.insertDates(tripId, freePlaces, trip.getDates());
        trip.setDates(mapper.findDates(tripId));
    }

    private void insertPlaces(SqlSession sqlSession, Trip trip) {
        for (TripDate tripDate : trip.getDates()) {
            tripDate.setPlaces(trip.getBus().getPlaceCount());
            PlaceSqlMapper mapper = sqlSession.getMapper(PlaceSqlMapper.class);
            mapper.insertPlaces(tripDate.getPlaces());
            tripDate.setPlaces(mapper.findPlaces(tripDate.getId()));
        }
    }

    @Override
    public void update(int tripId, Trip trip) {
        log.debug("DAO update Trip {}", trip);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                TripSqlMapper mapper = sqlSession.getMapper(TripSqlMapper.class);
                mapper.update(tripId, trip);
                Schedule schedule = trip.getSchedule();
                if (schedule == null) {
                    mapper.deleteSchedule(tripId);
                } else {
                    mapper.updateSchedule(tripId, schedule);
                }
                mapper.deleteDates(tripId);
                insertDates(trip, mapper);
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.UPDATE_FAILED;
                log.debug(errorCode.getMessage(), ex);
                sqlSession.rollback();
                throw new DatabaseException(errorCode);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void approve(int id) {
        log.debug("DAO approve Trip {}", id);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                TripSqlMapper mapper = sqlSession.getMapper(TripSqlMapper.class);
                mapper.approve(id);
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.UPDATE_FAILED;
                log.debug(errorCode.getMessage(), ex);
                sqlSession.rollback();
                throw new DatabaseException(errorCode);
            }
            sqlSession.commit();
        }
    }

    @Override
    public Trip findByKey(int id) {
        log.debug("DAO get Trip by Id {}", id);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                return sqlSession.getMapper(TripSqlMapper.class).findByKey(id);
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.GET_FAILED;
                log.debug(errorCode.getMessage(), ex);
                throw new DatabaseException(errorCode);
            }
        }
    }

    @Override
    public List<Trip> findAll() {
        log.debug("DAO get all Trips");
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                return sqlSession.getMapper(TripSqlMapper.class).findAll();
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.GET_ALL_FAILED;
                log.debug(errorCode.getMessage(), ex);
                throw new DatabaseException(errorCode);
            }
        }
    }

    @Override
    public void delete(int id) {
        log.debug("DAO delete trip");
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                sqlSession.getMapper(TripSqlMapper.class).delete(id);
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.DELETE_FAILED;
                log.debug(errorCode.getMessage(), ex);
                sqlSession.rollback();
                throw new DatabaseException(errorCode);
            }
            sqlSession.commit();
        }
    }

}
