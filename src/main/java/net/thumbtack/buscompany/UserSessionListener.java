package net.thumbtack.buscompany;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dao.SessionDao;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@AllArgsConstructor
@Component
public class UserSessionListener implements HttpSessionListener {
    private String sessionId;
    private final SessionDao dao;

    public void sessionCreated(final HttpSessionEvent event) {
        sessionId = event.getSession().getId();
    }

    public void sessionDestroyed(final HttpSessionEvent event) {
        dao.delete(sessionId);
    }

}
