package net.thumbtack.buscompany.integrationTests;

import net.thumbtack.buscompany.dto.BusDtoResponse;
import net.thumbtack.buscompany.dto.user.AdminDtoRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static net.thumbtack.buscompany.InsertEntities.registerUser;
import static net.thumbtack.buscompany.InstancesForTests.getAdminRequest;
import static net.thumbtack.buscompany.InstancesForTests.getBusList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BusTest {
    private static final RestTemplate template = new RestTemplate();
    private static final String url = "http://localhost:8888/api/";

    @BeforeAll
    @AfterAll
    public void clearDataBase() {
        template.delete(url + "debug/clear");
    }

    @Test
    public void getBusListTest() {
        AdminDtoRequest admin = getAdminRequest();
        HttpHeaders headers = registerUser("admins", admin);

        ResponseEntity<List<BusDtoResponse>> response = template.exchange(url + "buses", HttpMethod.GET,
                new HttpEntity<String>(headers), new ParameterizedTypeReference<>() {});
        assertEquals(200, response.getStatusCode().value());
        assertEquals(getBusList(), response.getBody());
    }

}
