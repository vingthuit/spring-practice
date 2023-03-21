package net.thumbtack.buscompany.integrationTests;

import net.thumbtack.buscompany.dto.user.*;
import net.thumbtack.buscompany.security.exceptions.ErrorCode;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static net.thumbtack.buscompany.InsertEntities.registerUser;
import static net.thumbtack.buscompany.InstancesForTests.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserTest {
    private static final RestTemplate template = new RestTemplate();
    private static final String url = "http://localhost:8888/api/";

    @BeforeAll
    @AfterEach
    void clearDataBase() {
        template.delete(url + "debug/clear");
    }

    @RepeatedTest(2)
    void registerSameLoginTest() {
        try {
            AdminDtoResponse registered = template.postForObject(url + "admins", getAdminRequest(), AdminDtoResponse.class);
            assertNotNull(registered);
            registered.setUserId(1);
            assertEquals(getAdminResponse(), registered);
        } catch (HttpClientErrorException e) {
            assertEquals(400, e.getStatusCode().value());
            assertTrue(Objects.requireNonNull(e.getMessage()).contains("invalid login"));
        }
    }

    @Test
    void deleteFailTest() {
        HttpHeaders headers = registerUser("admins", getAdminRequest());
        try {
            template.exchange(url + "accounts", HttpMethod.DELETE, new HttpEntity<String>(headers), String.class);
            fail();
        } catch (HttpClientErrorException e) {
            assertEquals(400, e.getStatusCode().value());
            assertTrue(Objects.requireNonNull(e.getMessage()).contains(ErrorCode.DELETE_ADMIN_FAILED.name()));
        }
    }

    @Test
    void deleteTest() {
        AdminDtoRequest admin = getAdminRequest();
        registerUser("admins", admin);
        admin.setLogin("new");
        HttpHeaders headers = registerUser("admins", admin);
        ResponseEntity<String> deleteResponse = template.exchange(url + "accounts", HttpMethod.DELETE,
                new HttpEntity<String>(headers), String.class);
        assertEquals(200, deleteResponse.getStatusCode().value());

        try {
            template.postForObject(url + "sessions", new LoginDtoRequest(admin.getLogin(), admin.getPassword()), String.class);
        } catch (HttpClientErrorException e) {
            assertEquals(400, e.getStatusCode().value());
            assertTrue(Objects.requireNonNull(e.getMessage()).contains(ErrorCode.GET_USER_FAILED.name()));
        }
    }

    @Test
    void getInfoTest() {
        HttpHeaders headers = registerUser("admins", getAdminRequest());
        ResponseEntity<AdminDtoResponse> response = template.exchange(url + "accounts", HttpMethod.GET,
                new HttpEntity<String>(headers), AdminDtoResponse.class);
        assertEquals(200, response.getStatusCode().value());
        Objects.requireNonNull(response.getBody()).setUserId(1);
        assertEquals(getAdminResponse(), response.getBody());
    }

    @Test
    void loginTest() {
        HttpHeaders headers = registerUser("clients", getClientRequest());
        ResponseEntity<ClientDtoResponse> response = template.exchange(url + "sessions", HttpMethod.POST,
                new HttpEntity<>(getLoginClientRequest(), headers), ClientDtoResponse.class);
        assertEquals(200, response.getStatusCode().value());
        Objects.requireNonNull(response.getBody()).setUserId(2);
        assertEquals(getClientResponse(), response.getBody());
        try {
            template.exchange(url + "sessions", HttpMethod.POST,
                    new HttpEntity<>(getLoginAdminRequest(), headers), String.class);
        } catch (HttpClientErrorException e) {
            assertEquals(400, e.getStatusCode().value());
            assertTrue(Objects.requireNonNull(e.getMessage()).contains("BAD_REQUEST"));
        }
    }

    @Test
    void logoutTest() {
        HttpHeaders headers = registerUser("clients", getClientRequest());
        ResponseEntity<String> response = template.exchange(url + "sessions", HttpMethod.DELETE,
                new HttpEntity<>(headers), String.class);
        assertEquals(200, response.getStatusCode().value());
        headers = response.getHeaders();
        try {
            template.exchange(url + "accounts", HttpMethod.GET,
                    new HttpEntity<String>(headers), ClientDtoResponse.class);
        } catch (HttpClientErrorException e) {
            assertEquals(400, e.getStatusCode().value());
            assertEquals("Bad Request", e.getStatusCode().getReasonPhrase());
        }
    }

}
