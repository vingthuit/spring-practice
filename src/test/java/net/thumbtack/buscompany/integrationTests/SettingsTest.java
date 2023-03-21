package net.thumbtack.buscompany.integrationTests;

import net.thumbtack.buscompany.dto.SettingsDtoResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static net.thumbtack.buscompany.InstancesForTests.*;
import static net.thumbtack.buscompany.InsertEntities.registerUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SettingsTest {
    private static final RestTemplate template = new RestTemplate();
    private static final String url = "http://localhost:8888/api/";

    @BeforeAll
    @AfterAll
    public void clearDataBase() {
        template.delete(url + "debug/clear");
    }

    @Test
    public void getSettingsTest() {
        SettingsDtoResponse response = template.getForObject(url + "settings", SettingsDtoResponse.class);
        assertEquals(getSettingsResponse(), response);
    }

    @Test
    public void getAdminSettingsTest() {
        HttpHeaders headers = registerUser("admins", getAdminRequest());
        ResponseEntity<SettingsDtoResponse> response = template.exchange(url + "settings", HttpMethod.GET,
                new HttpEntity<String>(headers), SettingsDtoResponse.class);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(getSettingsResponse(), response.getBody());
    }

    @Test
    public void getClientSettingsTest() {
        HttpHeaders headers = registerUser("clients", getClientRequest());
        ResponseEntity<SettingsDtoResponse> response = template.exchange(url + "settings", HttpMethod.GET,
                new HttpEntity<String>(headers), SettingsDtoResponse.class);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(getSettingsResponse(), response.getBody());
    }

}
