package net.thumbtack.buscompany.dao;

import net.thumbtack.buscompany.models.Bus;
import org.mapstruct.Named;

import java.util.List;

public interface BusDao {

    @Named("getBus")
    Bus findByKey(String name);

    List<Bus> findAll();
}
