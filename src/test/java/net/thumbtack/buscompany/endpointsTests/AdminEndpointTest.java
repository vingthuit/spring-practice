package net.thumbtack.buscompany.endpointsTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.buscompany.dao.UserDao;
import net.thumbtack.buscompany.dto.user.AdminDtoRequest;
import net.thumbtack.buscompany.dto.user.AdminDtoResponse;
import net.thumbtack.buscompany.dto.user.update.AdminUpdateDtoRequest;
import net.thumbtack.buscompany.endpoints.AdminEndpoint;
import net.thumbtack.buscompany.security.exceptions.GlobalErrorHandler;
import net.thumbtack.buscompany.security.PasswordEncoder;
import net.thumbtack.buscompany.services.AdminService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static net.thumbtack.buscompany.InstancesForTests.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AdminEndpoint.class)
public class AdminEndpointTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AdminService adminService;
    @MockBean
    private SessionService sessionService;
    @MockBean
    private PasswordEncoder encoder;
    @MockBean
    private UserDao userDao;

    @Test
    public void testRegister() throws Exception {
        AdminDtoRequest request = getAdminRequest();
        doReturn(request.getPassword()).when(encoder).encode(request.getPassword(), null);
        doReturn(getAdminResponse()).when(adminService).register(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admins")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isOk());
    }

    @Test
    public void testRegisterFail() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/admins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AdminDtoRequest())))
                        .andReturn();
        assertEquals(result.getResponse().getStatus(), 400);
        GlobalErrorHandler.ErrorsList error = objectMapper.readValue(result.getResponse().getContentAsString(), GlobalErrorHandler.ErrorsList.class);
        assertEquals(7, error.getErrors().size());
    }

    @Test
    public void testUpdate() throws Exception {
        AdminUpdateDtoRequest request = new AdminUpdateDtoRequest("имя", "фамилия", null,
                "пароль123", "новый_пароль", "главный");
        AdminDtoResponse response = new AdminDtoResponse(1, "imya", "familia", null,
                "новый_пароль", "glavnyy");

        doReturn(response).when(adminService).update(getCookie().getValue(), request);
        AdminDtoResponse updated = adminService.update(getCookie().getValue(), request);
        assertEquals(updated, response);
        Mockito.verify(adminService).update(getCookie().getValue(), request);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/admins")
                        .cookie(getCookie())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isOk());
    }

    @Test
    public void testUpdateFail() throws Exception {
        AdminUpdateDtoRequest request = new AdminUpdateDtoRequest(
                "имяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяя", "фамилия",
                "отчество", "пароль123",
                "новый пароль", "главный");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/admins")
                        .cookie(getCookie())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                        .andReturn();
        assertEquals(result.getResponse().getStatus(), 400);
        GlobalErrorHandler.ErrorsList error = objectMapper.readValue(result.getResponse().getContentAsString(), GlobalErrorHandler.ErrorsList.class);
        assertEquals(1, error.getErrors().size());
    }

}

