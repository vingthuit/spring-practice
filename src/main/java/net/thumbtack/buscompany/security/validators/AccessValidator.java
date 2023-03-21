package net.thumbtack.buscompany.security.validators;

import net.thumbtack.buscompany.dao.UserDao;
import net.thumbtack.buscompany.models.*;
import net.thumbtack.buscompany.security.PasswordEncoder;
import net.thumbtack.buscompany.security.exceptions.AccessException;
import net.thumbtack.buscompany.security.exceptions.ErrorCode;
import net.thumbtack.buscompany.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccessValidator {
    @Autowired
    private UserDao userDao;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User isValid(String cookie) {
        SessionAttributes session = sessionService.getSession(cookie);
        User user = userDao.findById(session.getUserId());
        if (user == null) {
            throw new AccessException(ErrorCode.NOT_ACCEPTABLE);
        }
        return user;
    }

    public void isAdmin(String cookie) {
        User user = isValid(cookie);
        if (!user.getUserType().equals(UserType.ADMIN)) {
            throw new AccessException(ErrorCode.NOT_ADMIN);
        }
    }

    public Client isClient(String cookie) {
        User user = isValid(cookie);
        if (!user.getUserType().equals(UserType.CLIENT)) {
            throw new AccessException(ErrorCode.NOT_CLIENT);
        }
        return (Client) user;
    }

    public User verifyPassword(String cookie, String oldPassword) {
        User user = isValid(cookie);
        String encodedPassword = passwordEncoder.encode(oldPassword, user.getSalt());
        if (!user.getPassword().equals(encodedPassword)) {
            throw new AccessException(ErrorCode.WRONG_PASSWORD);
        }
        return user;
    }
}
