package net.thumbtack.buscompany.daoImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.thumbtack.buscompany.dao.ClearDao;
import net.thumbtack.buscompany.security.exceptions.DatabaseException;
import net.thumbtack.buscompany.security.exceptions.ErrorCode;
import net.thumbtack.buscompany.sqlMappers.ClearBaseSqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class ClearDaoImpl implements ClearDao {
    private final SqlSessionFactory sqlSessionFactory;

    @Override
    public void deleteAll() {
        log.debug("DAO delete all");
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                ClearBaseSqlMapper mapper = sqlSession.getMapper(ClearBaseSqlMapper.class);
                mapper.deleteUsers();
                mapper.deleteTrips();
                mapper.deleteOrders();
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.CLEAR_DATABASE_FAILED;
                log.debug(errorCode.getMessage(), ex);
                sqlSession.rollback();
                throw new DatabaseException(errorCode);
            }
            sqlSession.commit();
        }
    }

}
