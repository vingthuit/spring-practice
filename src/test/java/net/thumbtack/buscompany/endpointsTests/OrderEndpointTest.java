package net.thumbtack.buscompany.endpointsTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.buscompany.dao.TripDao;
import net.thumbtack.buscompany.dto.OrderDtoRequest;
import net.thumbtack.buscompany.dto.OrderDtoResponse;
import net.thumbtack.buscompany.endpoints.OrderEndpoint;
import net.thumbtack.buscompany.security.exceptions.GlobalErrorHandler;
import net.thumbtack.buscompany.services.OrderService;
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
import org.springframework.util.LinkedMultiValueMap;

import static net.thumbtack.buscompany.InstancesForTests.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = OrderEndpoint.class)
public class OrderEndpointTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;
    @MockBean
    private TripDao tripDao;

    @Test
    void orderTicketTest() throws Exception {
        OrderDtoRequest request = getOrderRequest(1, 1);
        OrderDtoResponse response = getOrderResponse(1, 1, 1);

        doReturn(response).when(orderService).orderTicket(getCookie().getValue(), request);

        OrderDtoResponse serviceResponse = orderService.orderTicket(getCookie().getValue(), request);
        Mockito.verify(orderService).orderTicket(getCookie().getValue(), request);
        assertEquals(response, serviceResponse);

        mockMvc.perform(post("/api/orders")
                        .cookie(getCookie())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isOk());
    }

    @Test
    void orderTicketTestFail() throws Exception {
        OrderDtoRequest request = new OrderDtoRequest();
        MvcResult result = mockMvc.perform(post("/api/orders")
                        .cookie(getCookie())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                        .andReturn();
        assertEquals(result.getResponse().getStatus(), 400);
        GlobalErrorHandler.ErrorsList error = objectMapper.readValue(result.getResponse().getContentAsString(), GlobalErrorHandler.ErrorsList.class);
        assertEquals(1, error.getErrors().size());
    }

    @Test
    void getOrderListTest() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("clientId", "1");

        mockMvc.perform(get("/api/orders")
                        .cookie(getCookie())
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());
    }

    @Test
    void deleteOrderTest() throws Exception {
        OrderDtoRequest request = getOrderRequest(1, 1);
        doReturn(getOrderResponse(1, 1, 1)).when(orderService).orderTicket(getCookie().getValue(), request);
        mockMvc.perform(delete("/api/orders/1")
                        .cookie(getCookie())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());
    }

}
