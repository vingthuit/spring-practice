package net.thumbtack.buscompany.services;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dao.SessionDao;
import net.thumbtack.buscompany.dto.user.LoginDtoRequest;
import net.thumbtack.buscompany.mappers.SessionMapper;
import net.thumbtack.buscompany.models.SessionAttributes;
import net.thumbtack.buscompany.models.User;
import net.thumbtack.buscompany.security.validators.authorization.LoginValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@AllArgsConstructor
@Service
public class SessionService {
    private final LoginValidator loginValidator;
    @Value("${user_idle_timeout}")
    private final int timeout;
    private final SessionDao sessionDao;
    private final SessionMapper sessionMapper;

    public String login(LoginDtoRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        User user = loginValidator.isValid(loginRequest);
        return setSessionAttributes(user.getUserId(), request, response);
    }

    public String setSessionAttributes(int userId, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        session.invalidate();

        session = request.getSession(true);
        session.setAttribute("userId", userId);
        session.setMaxInactiveInterval(timeout);

        sessionDao.insert(sessionMapper.toModel(session));

        Cookie cookie = new Cookie("JAVASESSIONID", session.getId());
        cookie.setMaxAge(timeout);
        response.addCookie(cookie);

        return cookie.getValue();
    }

    public SessionAttributes getSession(String sessionId) {
        return sessionDao.findByKey(sessionId);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        Cookie cookie = new Cookie("JAVASESSIONID", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        session.invalidate();
    }

}
