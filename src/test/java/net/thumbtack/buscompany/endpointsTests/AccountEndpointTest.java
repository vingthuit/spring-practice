package net.thumbtack.buscompany.endpointsTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.buscompany.endpoints.AccountEndpoint;
import net.thumbtack.buscompany.services.AccountService;
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

import javax.servlet.http.HttpServletResponse;

import static net.thumbtack.buscompany.InstancesForTests.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountEndpoint.class)
public class AccountEndpointTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AccountService accountService;
    @MockBean
    private SessionService sessionService;

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/accounts")
                        .cookie(getCookie())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(Mockito.mock(HttpServletResponse.class))))
                        .andExpect(status().isOk());
    }

    @Test
    public void testGetUserInfo() throws Exception {
        mockMvc.perform(get("/api/accounts").cookie(getCookie())).andExpect(status().isOk());
    }

}
