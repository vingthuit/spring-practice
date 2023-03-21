package net.thumbtack.buscompany.daoImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.thumbtack.buscompany.dao.BusDao;
import net.thumbtack.buscompany.security.exceptions.DatabaseException;
import net.thumbtack.buscompany.models.Bus;
import net.thumbtack.buscompany.security.exceptions.ErrorCode;
import net.thumbtack.buscompany.sqlMappers.BusSqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class BusDaoImpl implements BusDao {

    private final SqlSessionFactory sqlSessionFactory;

    @Override
    public Bus findByKey(String name) {
        log.debug("DAO get Bus by name {}", name);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                return sqlSession.getMapper(BusSqlMapper.class).findByKey(name);
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.GET_FAILED;
                log.debug(errorCode.getMessage(), ex);
                throw new DatabaseException(errorCode);
            }
        }
    }

    @Override
    public List<Bus> findAll() {
        log.debug("DAO get all Buses");
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                return sqlSession.getMapper(BusSqlMapper.class).findAll();
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.GET_ALL_FAILED;
                log.debug(errorCode.getMessage(), ex);
                throw new DatabaseException(errorCode);
            }
        }
    }

}
