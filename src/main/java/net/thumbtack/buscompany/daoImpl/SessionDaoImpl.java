package net.thumbtack.buscompany.daoImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.thumbtack.buscompany.dao.SessionDao;
import net.thumbtack.buscompany.security.exceptions.DatabaseException;
import net.thumbtack.buscompany.models.SessionAttributes;
import net.thumbtack.buscompany.security.exceptions.ErrorCode;
import net.thumbtack.buscompany.sqlMappers.SessionSqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class SessionDaoImpl implements SessionDao {
    private final SqlSessionFactory sqlSessionFactory;

    @Override
    public void insert(SessionAttributes sessionAttributes) {
        log.debug("DAO insert Session Attributes {}", sessionAttributes);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                sqlSession.getMapper(SessionSqlMapper.class).insert(sessionAttributes);
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.INSERT_FAILED;
                log.debug(errorCode.getMessage(), ex);
                sqlSession.rollback();
                throw new DatabaseException(errorCode);
            }
            sqlSession.commit();
        }
    }

    @Override
    public SessionAttributes findByKey(String id) {
        log.debug("DAO get Session Attributes by id {}", id);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                return sqlSession.getMapper(SessionSqlMapper.class).findByKey(id);
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.GET_FAILED;
                log.debug(errorCode.getMessage(), ex);
                throw new DatabaseException(errorCode);
            }
        }
    }

    @Override
    public void delete(String id) {
        log.debug("DAO delete Session Attributes by id {}", id);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                sqlSession.getMapper(SessionSqlMapper.class).delete(id);
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
