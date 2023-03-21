package net.thumbtack.buscompany.daoImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.thumbtack.buscompany.dao.PlaceDao;
import net.thumbtack.buscompany.models.BusPlace;
import net.thumbtack.buscompany.security.exceptions.DatabaseException;
import net.thumbtack.buscompany.security.exceptions.ErrorCode;
import net.thumbtack.buscompany.security.exceptions.OrderException;
import net.thumbtack.buscompany.sqlMappers.PlaceSqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class PlaceDaoImpl implements PlaceDao {

    private final SqlSessionFactory sqlSessionFactory;

    @Override
    public void takePlace(BusPlace busPlace) {
        log.debug("DAO take place {}", busPlace.getPlace());
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            boolean updated;
            try {
                PlaceSqlMapper mapper = sqlSession.getMapper(PlaceSqlMapper.class);
                checkPreviousPlace(mapper, busPlace);
                updated = mapper.update(busPlace);
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.INSERT_FAILED;
                log.debug(errorCode.getMessage(), ex);
                sqlSession.rollback();
                throw new DatabaseException(errorCode);
            }
            if (!updated) {
                sqlSession.rollback();
                throw new OrderException(ErrorCode.PLACE_TAKEN);
            }
            sqlSession.commit();
        }
    }

    private void checkPreviousPlace(PlaceSqlMapper mapper, BusPlace busPlace) {
        int tripDateId = busPlace.getTripDateId();
        BusPlace takenPlace = mapper.findPlace(tripDateId, busPlace.getPassengerId());
        if (takenPlace != null) {
             mapper.freePlace(takenPlace);
        }
    }

}
