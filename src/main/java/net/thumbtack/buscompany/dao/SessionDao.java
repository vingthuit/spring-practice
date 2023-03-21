package net.thumbtack.buscompany.dao;

import net.thumbtack.buscompany.models.SessionAttributes;

public interface SessionDao {
    void insert(SessionAttributes session);

    SessionAttributes findByKey(String id);

    void delete(String id);
}
