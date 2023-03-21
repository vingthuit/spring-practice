package net.thumbtack.buscompany.dao;

import net.thumbtack.buscompany.models.Admin;

public interface AdminDao {
    void insert(Admin admin);

    void update(Admin admin);

    Admin findByKey(String login);

    Long getCount();

    void delete(int id);
}
