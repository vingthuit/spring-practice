package net.thumbtack.buscompany.endpointsTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.buscompany.dao.BusDao;
import net.thumbtack.buscompany.dto.trip.TripDtoRequest;
import net.thumbtack.buscompany.endpoints.TripEndPoint;
import net.thumbtack.buscompany.security.exceptions.GlobalErrorHandler;
import net.thumbtack.buscompany.services.TripService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static net.thumbtack.buscompany.InstancesForTests.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TripEndPoint.class)
public class TripEndPointTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TripService tripService;
    @MockBean
    private BusDao busDao;

    @Test
    public void testAddTrip() throws Exception {
        mockMvc.perform(post("/api/trips")
                        .cookie(getCookie())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(getTripRequest())))
                        .andExpect(status().isOk());
    }

    @Test
    public void testAddTripFail() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/trips")
                        .cookie(getCookie())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new TripDtoRequest())))
                        .andReturn();
        assertEquals(result.getResponse().getStatus(), 400);
        GlobalErrorHandler.ErrorsList error = objectMapper.readValue(result.getResponse().getContentAsString(), GlobalErrorHandler.ErrorsList.class);
        assertEquals(7, error.getErrors().size());
    }

    @Test
    public void testUpdateTrip() throws Exception {
        mockMvc.perform(put("/api/trips/1")
                        .cookie(getCookie())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(getTripRequest())))
                        .andExpect(status().isOk());
    }

    @Test
    public void testApproveTrip() throws Exception {
        mockMvc.perform(put("/api/trips/1/approve")
                        .cookie(getCookie())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTrip() throws Exception {
        mockMvc.perform(delete("/api/trips/1")
                        .cookie(getCookie())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());
    }

    @Test
    public void testGetTrip() throws Exception {
        mockMvc.perform(get("/api/trips/1")
                        .cookie(getCookie())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());
    }

    @Test
    public void testGetList() throws Exception {
        mockMvc.perform(get("/api/trips/1")
                        .cookie(getCookie())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("bus", "автобус"))
                        .andExpect(status().isOk());
    }

}
