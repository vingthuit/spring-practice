package net.thumbtack.buscompany.dao;

import net.thumbtack.buscompany.models.User;

public interface UserDao {

    User findByKey(String login);

    User findById(int userId);

}
