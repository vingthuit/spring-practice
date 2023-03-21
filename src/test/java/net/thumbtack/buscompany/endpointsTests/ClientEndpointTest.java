package net.thumbtack.buscompany.endpointsTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.buscompany.dao.UserDao;
import net.thumbtack.buscompany.dto.user.ClientDtoRequest;
import net.thumbtack.buscompany.dto.user.ClientDtoResponse;
import net.thumbtack.buscompany.dto.user.update.ClientUpdateDtoRequest;
import net.thumbtack.buscompany.endpoints.ClientEndpoint;
import net.thumbtack.buscompany.security.exceptions.GlobalErrorHandler;
import net.thumbtack.buscompany.security.PasswordEncoder;
import net.thumbtack.buscompany.services.ClientService;
import net.thumbtack.buscompany.services.SessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static net.thumbtack.buscompany.InstancesForTests.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ClientEndpoint.class)
public class ClientEndpointTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ClientService clientService;
    @MockBean
    private SessionService sessionService;
    @MockBean
    private PasswordEncoder encoder;
    @MockBean
    private UserDao userDao;

    @Test
    public void testRegister() throws Exception {
        ClientDtoRequest request = getClientRequest();
        doReturn(request.getPassword()).when(encoder).encode(request.getPassword(), null);
        doReturn(getClientResponse()).when(clientService).register(request);
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isOk());
    }

    @Test
    public void testRegisterFail() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ClientDtoRequest())))
                        .andReturn();
        assertEquals(result.getResponse().getStatus(), 400);
        GlobalErrorHandler.ErrorsList error = objectMapper.readValue(result.getResponse().getContentAsString(), GlobalErrorHandler.ErrorsList.class);
        assertEquals(7, error.getErrors().size());
    }

    @Test
    public void testGetAll() throws Exception {
        List<ClientDtoResponse> clients = new ArrayList<>() {{
            add(getClientResponse());
            add(getClientResponse());
        }};
        doReturn(clients).when(clientService).getAll(getCookie().getValue());
        List<ClientDtoResponse> response = clientService.getAll(getCookie().getValue());
        assertEquals(clients, response);
        Mockito.verify(clientService).getAll(getCookie().getValue());

        MvcResult result = mockMvc.perform(get("/api/clients").cookie(getCookie())).andReturn();
        assertEquals(result.getResponse().getStatus(), 200);
        String strResult = result.getResponse().getContentAsString();
        int clientCount = strResult.split("userId", -1).length - 1;
        assertEquals(clientCount, 2);
    }

    @Test
    public void testUpdate() throws Exception {
        ClientUpdateDtoRequest request = new ClientUpdateDtoRequest("имя", "фамилия",
                "отчество", "пароль123", "новый_пароль", "c@mail.ru", "+7-9007007070");
        ClientDtoResponse response = getClientResponse();

        doReturn(response).when(clientService).update(getCookie().getValue(), request);
        ClientDtoResponse updated = clientService.update(getCookie().getValue(), request);
        assertEquals(updated, response);
        Mockito.verify(clientService).update(getCookie().getValue(), request);

        mockMvc.perform(put("/api/clients")
                        .cookie(getCookie())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isOk());
    }

    @Test
    public void testUpdateFail() throws Exception {
        MvcResult result = mockMvc.perform(put("/api/clients")
                        .cookie(getCookie())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ClientUpdateDtoRequest())))
                        .andReturn();
        assertEquals(result.getResponse().getStatus(), 400);
        GlobalErrorHandler.ErrorsList error = objectMapper.readValue(result.getResponse().getContentAsString(), GlobalErrorHandler.ErrorsList.class);
        assertEquals(7, error.getErrors().size());
    }

}
