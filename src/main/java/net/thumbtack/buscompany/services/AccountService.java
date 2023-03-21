package net.thumbtack.buscompany.services;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dao.AdminDao;
import net.thumbtack.buscompany.dao.ClientDao;
import net.thumbtack.buscompany.dto.user.UserDtoResponse;
import net.thumbtack.buscompany.mappers.AdminMapper;
import net.thumbtack.buscompany.mappers.ClientMapper;
import net.thumbtack.buscompany.models.Admin;
import net.thumbtack.buscompany.models.Client;
import net.thumbtack.buscompany.models.User;
import net.thumbtack.buscompany.security.validators.AccessValidator;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AccountService {
    private final AccessValidator validator;
    private final AdminMapper adminMapper;
    private final ClientMapper clientMapper;
    private final AdminDao adminDao;
    private final ClientDao clientDao;

    public void deleteUser(String cookie) {
        User user = validator.isValid(cookie);
        switch (user.getUserType()) {
            case ADMIN:
                adminDao.delete(user.getUserId());
                break;
            case CLIENT:
                clientDao.delete(user.getUserId());
                break;
        }
    }

    public UserDtoResponse getUser(String cookie) {
        User user = validator.isValid(cookie);
        switch (user.getUserType()) {
            case ADMIN:
                return adminMapper.toDTO((Admin) user);
            case CLIENT:
                return clientMapper.toDTO((Client) user);
        }
        return new UserDtoResponse();
    }

}
