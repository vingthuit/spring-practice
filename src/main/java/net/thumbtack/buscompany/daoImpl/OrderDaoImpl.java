package net.thumbtack.buscompany.daoImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.thumbtack.buscompany.dao.OrderDao;
import net.thumbtack.buscompany.security.exceptions.DatabaseException;
import net.thumbtack.buscompany.models.Order;
import net.thumbtack.buscompany.security.exceptions.ErrorCode;
import net.thumbtack.buscompany.security.exceptions.OrderException;
import net.thumbtack.buscompany.sqlMappers.OrderSqlMapper;
import net.thumbtack.buscompany.sqlMappers.TripSqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class OrderDaoImpl implements OrderDao {

    private final SqlSessionFactory sqlSessionFactory;

    @Override
    public void insert(Order order) {
        log.debug("DAO insert Order {}", order);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            boolean canTakePlaces = checkPlaces(sqlSession, order);
            if (!canTakePlaces) {
                sqlSession.rollback();
                throw new OrderException(ErrorCode.NO_FREE_PLACES);
            }
            try {
                OrderSqlMapper mapper = sqlSession.getMapper(OrderSqlMapper.class);
                int tripDateId = order.getTripDate().getId();
                mapper.insert(order, tripDateId);
                mapper.insertPassengers(order.getId(), order.getPassengers());
                order.setPassengers(mapper.findPassengers(order));
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.INSERT_FAILED;
                log.debug(errorCode.getMessage(), ex);
                sqlSession.rollback();
                throw new DatabaseException(errorCode);
            }
            sqlSession.commit();
        }
    }

    private boolean checkPlaces(SqlSession sqlSession, Order order) {
        TripSqlMapper mapper = sqlSession.getMapper(TripSqlMapper.class);
        return mapper.updateTripDate(order.getTripDate(), order.getPassengers().size());
    }

    @Override
    public Order findByKey(int id) {
        log.debug("DAO get Order by Id {}", id);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                return sqlSession.getMapper(OrderSqlMapper.class).findByKey(id);
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.GET_FAILED;
                log.debug(errorCode.getMessage(), ex);
                throw new DatabaseException(errorCode);
            }
        }
    }

    @Override
    public List<Order> findAll() {
        log.debug("DAO get all orders");
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                return sqlSession.getMapper(OrderSqlMapper.class).findAll();
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.GET_ALL_FAILED;
                log.debug(errorCode.getMessage(), ex);
                throw new DatabaseException(errorCode);
            }
        }
    }

    @Override
    public void delete(int id) {
        log.debug("DAO delete order");
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                sqlSession.getMapper(OrderSqlMapper.class).delete(id);
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
