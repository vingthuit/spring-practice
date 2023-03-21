package net.thumbtack.buscompany.services;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dao.BusDao;
import net.thumbtack.buscompany.dto.BusDtoResponse;
import net.thumbtack.buscompany.mappers.BusMapper;
import net.thumbtack.buscompany.security.validators.AccessValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BusService {
    private final AccessValidator accessValidator;
    private final BusDao busDao;
    private final BusMapper busMapper;

    public List<BusDtoResponse> getAllBuses(String cookie) {
        accessValidator.isAdmin(cookie);
        return busDao.findAll().stream().map(busMapper::toDTO).collect(Collectors.toList());
    }

    public BusDtoResponse getBus(String busName) {
        return busMapper.toDTO(busDao.findByKey(busName));
    }

}
