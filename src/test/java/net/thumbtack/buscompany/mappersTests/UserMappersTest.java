package net.thumbtack.buscompany.mappersTests;

import net.thumbtack.buscompany.dto.user.AdminDtoRequest;
import net.thumbtack.buscompany.dto.user.AdminDtoResponse;
import net.thumbtack.buscompany.dto.user.ClientDtoRequest;
import net.thumbtack.buscompany.dto.user.ClientDtoResponse;
import net.thumbtack.buscompany.mappers.*;
import net.thumbtack.buscompany.models.Admin;
import net.thumbtack.buscompany.models.Client;
import net.thumbtack.buscompany.services.TranslitToLatin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static net.thumbtack.buscompany.InstancesForTests.*;
import static net.thumbtack.buscompany.InstancesForTests.getClientResponse;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = {
        AdminMapperImpl.class,
        ClientMapperImpl.class,
        TranslitToLatin.class})
class UserMappersTest {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private ClientMapper clientMapper;

    @Test
    void testAdminMapping() {
        AdminDtoRequest dtoRequest = getAdminRequest();
        Admin admin = adminMapper.toModel(dtoRequest, new byte[]{4, 15, 2});
        admin.setUserId(1);
        assertEquals(getAdmin(), admin);

        AdminDtoResponse response = adminMapper.toDTO(admin);
        assertEquals(getAdminResponse(), response);
    }

    @Test
    void testClientMapping() {
        ClientDtoRequest dtoRequest = getClientRequest();
        Client client = clientMapper.toModel(dtoRequest, new byte[]{1, 2, 3});
        client.setUserId(2);
        assertEquals(getClient(), client);

        ClientDtoResponse response = clientMapper.toDTO(client);
        assertEquals(getClientResponse(), response);
    }
}
