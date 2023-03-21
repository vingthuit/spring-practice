package net.thumbtack.buscompany.daoImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.thumbtack.buscompany.dao.AdminDao;
import net.thumbtack.buscompany.models.User;
import net.thumbtack.buscompany.security.exceptions.DatabaseException;
import net.thumbtack.buscompany.models.Admin;
import net.thumbtack.buscompany.security.exceptions.DeleteException;
import net.thumbtack.buscompany.security.exceptions.ErrorCode;
import net.thumbtack.buscompany.sqlMappers.AdminSqlMapper;
import net.thumbtack.buscompany.sqlMappers.UserSqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class AdminDaoImpl implements AdminDao {
    private final SqlSessionFactory sqlSessionFactory;

    @Override
    public void insert(Admin admin) {
        log.debug("DAO insert User {}", admin);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                UserSqlMapper mapper = sqlSession.getMapper(UserSqlMapper.class);
                mapper.insertUser(admin.getLogin().toLowerCase(), admin);
                AdminSqlMapper adminMapper = sqlSession.getMapper(AdminSqlMapper.class);
                adminMapper.insert(admin);
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
    public void update(Admin admin) {
        log.debug("DAO update Admin {}", admin);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                AdminSqlMapper mapper = sqlSession.getMapper(AdminSqlMapper.class);
                mapper.update(admin);
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
    public Admin findByKey(String login) {
        log.debug("DAO get Admin by login {}", login);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                User user = sqlSession.getMapper(UserSqlMapper.class).findByKey(login.toLowerCase());
                if (user != null) {
                    return sqlSession.getMapper(AdminSqlMapper.class).findByKey(user.getUserId());
                }
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.GET_USER_FAILED;
                log.debug(errorCode.getMessage(), ex);
                throw new DatabaseException(ErrorCode.GET_USER_FAILED);
            }
        }
        return null;
    }

    @Override
    public Long getCount() {
        log.debug("DAO get count of admins");
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                return sqlSession.getMapper(AdminSqlMapper.class).getCount();
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.COUNT_FAILED;
                log.debug(errorCode.getMessage(), ex);
                throw new DatabaseException(errorCode);
            }
        }
    }

    @Override
    public void delete(int id) {
        log.debug("DAO delete Admin by id {}", id);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            AdminSqlMapper mapper = sqlSession.getMapper(AdminSqlMapper.class);
            try {
                mapper.delete(id);
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.DELETE_FAILED;
                log.debug(errorCode.getMessage(), ex);
                sqlSession.rollback();
                throw new DatabaseException(errorCode);
            }
            Long count = mapper.getCount();
            if (count < 1) {
                sqlSession.rollback();
                throw new DeleteException(ErrorCode.DELETE_ADMIN_FAILED);
            }
            sqlSession.commit();
        }
    }

}
