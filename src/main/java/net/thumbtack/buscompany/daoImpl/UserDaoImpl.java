package net.thumbtack.buscompany.daoImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.thumbtack.buscompany.dao.UserDao;
import net.thumbtack.buscompany.models.User;
import net.thumbtack.buscompany.security.exceptions.DatabaseException;
import net.thumbtack.buscompany.security.exceptions.ErrorCode;
import net.thumbtack.buscompany.sqlMappers.AdminSqlMapper;
import net.thumbtack.buscompany.sqlMappers.ClientSqlMapper;
import net.thumbtack.buscompany.sqlMappers.UserSqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class UserDaoImpl implements UserDao {
    private final SqlSessionFactory sqlSessionFactory;

    private User getUser(SqlSession sqlSession, User user) {
        User specificUser = null;
        int userId = user.getUserId();
        switch (user.getUserType()) {
            case ADMIN:
                specificUser = sqlSession.getMapper(AdminSqlMapper.class).findByKey(userId);
                break;
            case CLIENT:
                specificUser = sqlSession.getMapper(ClientSqlMapper.class).findByKey(userId);
                break;
        }
        if (specificUser == null) {
            throw new RuntimeException();
        }
        specificUser.setUserType(user.getUserType());
        specificUser.setLogin(user.getLogin());
        return specificUser;
    }

    @Override
    public User findByKey(String login) {
        log.debug("DAO get User by login {}", login);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                User user = sqlSession.getMapper(UserSqlMapper.class).findByKey(login.toLowerCase());
                if (user != null) {
                    user = getUser(sqlSession, user);
                }
                return user;
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.GET_USER_FAILED;
                log.debug(errorCode.getMessage(), ex);
                throw new DatabaseException(errorCode);
            }
        }
    }

    @Override
    public User findById(int userId) {
        log.debug("DAO get User by userId {}", userId);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            User user;
            try {
                user = sqlSession.getMapper(UserSqlMapper.class).findById(userId);
                if (user != null) {
                    user = getUser(sqlSession, user);
                }
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.GET_FAILED;
                log.debug(errorCode.getMessage(), ex);
                throw new DatabaseException(errorCode);
            }
            return user;
        }
    }

}
