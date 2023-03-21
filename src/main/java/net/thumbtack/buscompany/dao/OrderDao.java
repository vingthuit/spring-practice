package net.thumbtack.buscompany.dao;

import net.thumbtack.buscompany.models.Order;

import java.util.List;

public interface OrderDao {
    void insert(Order order);

    Order findByKey(int id);

    List<Order> findAll();

    void delete(int id);

}
