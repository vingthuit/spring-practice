package net.thumbtack.buscompany.dao;

import net.thumbtack.buscompany.models.Client;

import java.util.List;

public interface ClientDao {
    void insert(Client client);

    void update(Client client);

    Client findByKey(String login);

    List<Client> findAll();

    void delete(int id);
}
