package net.thumbtack.buscompany.services;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dao.ClientDao;
import net.thumbtack.buscompany.dto.user.ClientDtoRequest;
import net.thumbtack.buscompany.dto.user.ClientDtoResponse;
import net.thumbtack.buscompany.dto.user.update.ClientUpdateDtoRequest;
import net.thumbtack.buscompany.mappers.ClientMapper;
import net.thumbtack.buscompany.models.Client;
import net.thumbtack.buscompany.security.PasswordEncoder;
import net.thumbtack.buscompany.security.validators.AccessValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ClientService {
    private final AccessValidator validator;
    private final PasswordEncoder passwordEncoder;
    private final ClientDao clientDao;
    private final ClientMapper clientMapper;

    public ClientDtoResponse register(ClientDtoRequest request) {
        byte[] salt = passwordEncoder.getSalt();
        String hashedPassword = passwordEncoder.encode(request.getPassword(), salt);
        request.setPassword(hashedPassword);

        Client client = clientMapper.toModel(request, salt);
        clientDao.insert(client);
        return clientMapper.toDTO(client);
    }

    public ClientDtoResponse update(String cookie, ClientUpdateDtoRequest request) {
        Client client = (Client) validator.verifyPassword(cookie, request.getOldPassword());
        String hashedPassword = passwordEncoder.encode(request.getNewPassword(), client.getSalt());
        request.setNewPassword(hashedPassword);

        clientMapper.update(request, client);
        clientDao.update(client);
        return clientMapper.toDTO(client);
    }

    public List<ClientDtoResponse> getAll(String cookie) {
        validator.isAdmin(cookie);
        List<Client> clients = clientDao.findAll();
        return clients.stream().map(clientMapper::toDTO).collect(Collectors.toList());
    }
}