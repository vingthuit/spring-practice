package net.thumbtack.buscompany.daoImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.thumbtack.buscompany.dao.ClientDao;
import net.thumbtack.buscompany.models.User;
import net.thumbtack.buscompany.security.exceptions.DatabaseException;
import net.thumbtack.buscompany.models.Client;
import net.thumbtack.buscompany.security.exceptions.ErrorCode;
import net.thumbtack.buscompany.sqlMappers.ClientSqlMapper;
import net.thumbtack.buscompany.sqlMappers.UserSqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class ClientDaoImpl implements ClientDao {

    private final SqlSessionFactory sqlSessionFactory;

    @Override
    public void insert(Client client) {
        log.debug("DAO insert Client {}", client);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                UserSqlMapper mapper = sqlSession.getMapper(UserSqlMapper.class);
                mapper.insertUser(client.getLogin().toLowerCase(), client);
                ClientSqlMapper clientMapper = sqlSession.getMapper(ClientSqlMapper.class);
                clientMapper.insert(client);
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
    public void update(Client client) {
        log.debug("DAO update Client {}", client);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                sqlSession.getMapper(ClientSqlMapper.class).update(client);
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
    public Client findByKey(String login) {
        log.debug("DAO get Client by login {}", login);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                User user = sqlSession.getMapper(UserSqlMapper.class).findByKey(login.toLowerCase());
                if (user != null) {
                    return sqlSession.getMapper(ClientSqlMapper.class).findByKey(user.getUserId());
                }
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.GET_USER_FAILED;
                log.debug(errorCode.getMessage(), ex);
                throw new DatabaseException(errorCode);
            }
        }
        return null;
    }

    @Override
    public List<Client> findAll() {
        log.debug("DAO get all Clients");
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                return sqlSession.getMapper(ClientSqlMapper.class).findAll();
            } catch (RuntimeException ex) {
                ErrorCode errorCode = ErrorCode.GET_ALL_FAILED;
                log.debug(errorCode.getMessage(), ex);
                throw new DatabaseException(errorCode);
            }
        }
    }

    @Override
    public void delete(int id) {
        log.debug("DAO delete Client by id {}", id);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            try {
                sqlSession.getMapper(ClientSqlMapper.class).delete(id);
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
