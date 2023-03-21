package net.thumbtack.buscompany.dao;

import net.thumbtack.buscompany.models.Trip;

import java.util.List;

public interface TripDao {
    void insert(Trip trip);

    void update(int id, Trip trip);

    void approve(int id);

    Trip findByKey(int id);

    List<Trip> findAll();

    void delete(int id);
}
