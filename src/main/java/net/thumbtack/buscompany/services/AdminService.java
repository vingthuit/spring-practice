package net.thumbtack.buscompany.services;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dao.AdminDao;
import net.thumbtack.buscompany.dto.user.AdminDtoRequest;
import net.thumbtack.buscompany.dto.user.AdminDtoResponse;
import net.thumbtack.buscompany.dto.user.update.AdminUpdateDtoRequest;
import net.thumbtack.buscompany.mappers.AdminMapper;
import net.thumbtack.buscompany.models.Admin;
import net.thumbtack.buscompany.security.PasswordEncoder;
import net.thumbtack.buscompany.security.validators.AccessValidator;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AdminService {
    private final PasswordEncoder passwordEncoder;
    private final AccessValidator validator;
    private final AdminDao adminDao;
    private final AdminMapper adminMapper;

    public AdminDtoResponse register(AdminDtoRequest request) {
        byte[] salt = passwordEncoder.getSalt();
        String hashedPassword = passwordEncoder.encode(request.getPassword(), salt);
        request.setPassword(hashedPassword);

        Admin admin = adminMapper.toModel(request, salt);
        adminDao.insert(admin);
        return adminMapper.toDTO(admin);
    }

    public AdminDtoResponse update(String cookie, AdminUpdateDtoRequest request) {
        Admin admin = (Admin) validator.verifyPassword(cookie, request.getOldPassword());

        String hashedPassword = passwordEncoder.encode(request.getNewPassword(), admin.getSalt());
        request.setNewPassword(hashedPassword);

        adminMapper.update(request, admin);
        adminDao.update(admin);
        return adminMapper.toDTO(admin);
    }

}