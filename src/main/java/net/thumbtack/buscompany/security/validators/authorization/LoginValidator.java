package net.thumbtack.buscompany.security.validators.authorization;

import net.thumbtack.buscompany.dao.UserDao;
import net.thumbtack.buscompany.dto.user.LoginDtoRequest;
import net.thumbtack.buscompany.models.User;
import net.thumbtack.buscompany.security.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class LoginValidator {
    @Autowired
    private UserDao dao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User isValid(LoginDtoRequest request) {
        String login = request.getLogin();
        String password = request.getPassword();
        String errorMessage = "invalid login or password";
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }
        User user = dao.findByKey(login);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }
        String encodedPassword = passwordEncoder.encode(password, user.getSalt());
        if (!user.getPassword().equals(encodedPassword)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }
        return user;
    }
}
