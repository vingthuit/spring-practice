package net.thumbtack.buscompany.mappersTests;

import net.thumbtack.buscompany.dao.BusDao;
import net.thumbtack.buscompany.dto.trip.TripAdminDtoResponse;
import net.thumbtack.buscompany.dto.trip.TripDtoRequest;
import net.thumbtack.buscompany.mappers.*;
import net.thumbtack.buscompany.models.Trip;
import net.thumbtack.buscompany.services.TranslitToLatin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.thumbtack.buscompany.InstancesForTests.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = {
        TripMapperImpl.class,
        BusMapperImpl.class,
        TranslitToLatin.class})
class TripMapperTest {
    @Autowired
    private TripMapper tripMapper;
    @Autowired
    private BusMapper busMapper;
    @MockBean
    private BusDao busDao;

    @Test
    void testTripDateMapping() {
        TripDtoRequest dtoRequest = getTripRequest();
        doReturn(getBus()).when(busDao).findByKey(dtoRequest.getBusName());
        Trip trip = tripMapper.toModel(dtoRequest);
        trip.setId(1);
        trip.setDates(Collections.singletonList(getTripDate(1)));
        assertEquals(getTrip(), trip);

        TripAdminDtoResponse response = tripMapper.toDTOAdmin(trip);
        assertEquals(getTripResponse(1), response);
    }

    @Test
    void testTripScheduleDailyMapping() {
        TripDtoRequest dtoRequest = getTripRequest2();
        doReturn(getBus2()).when(busDao).findByKey(dtoRequest.getBusName());
        Trip trip = tripMapper.toModel(dtoRequest);
        trip.setId(2);
        trip.setDates(getDates(2));
        assertEquals(getTrip2(), trip);

        TripAdminDtoResponse response = tripMapper.toDTOAdmin(trip);
        assertEquals(getTripResponse2(), response);
    }

    @Test
    void testTripScheduleEvenMapping() {
        String period = "even";
        TripDtoRequest dtoRequest = getTripRequest2();
        dtoRequest.getSchedule().setPeriod(period);

        TripAdminDtoResponse tripResponse = getTripResponse2();
        List<LocalDate> dates = new ArrayList<>() {{
            add(LocalDate.parse("2022-11-28"));
            add(LocalDate.parse("2022-11-30"));
            add(LocalDate.parse("2022-12-02"));
        }};
        tripResponse.setDates(dates);
        tripResponse.getSchedule().setPeriod(period);

        doReturn(getBus2()).when(busDao).findByKey(dtoRequest.getBusName());
        Trip trip = tripMapper.toModel(dtoRequest);
        trip.setId(2);
        TripAdminDtoResponse response = tripMapper.toDTOAdmin(trip);
        assertEquals(tripResponse, response);
    }

    @Test
    void testTripScheduleOddMapping() {
        String period = "odd";
        TripDtoRequest dtoRequest = getTripRequest2();
        dtoRequest.getSchedule().setPeriod(period);

        TripAdminDtoResponse tripResponse = getTripResponse2();
        List<LocalDate> dates = new ArrayList<>() {{
            add(LocalDate.parse("2022-11-29"));
            add(LocalDate.parse("2022-12-01"));
        }};
        tripResponse.setDates(dates);
        tripResponse.getSchedule().setPeriod(period);

        doReturn(getBus2()).when(busDao).findByKey(dtoRequest.getBusName());
        Trip trip = tripMapper.toModel(dtoRequest);
        trip.setId(2);
        TripAdminDtoResponse response = tripMapper.toDTOAdmin(trip);
        assertEquals(tripResponse, response);
    }

    @Test
    void testTripScheduleWeekMapping() {
        String period = "MONDAY,TUESDAY,SUNDAY";
        TripDtoRequest dtoRequest = getTripRequest2();
        dtoRequest.getSchedule().setPeriod(period);

        TripAdminDtoResponse tripResponse = getTripResponse2();
        List<LocalDate> dates = new ArrayList<>() {{
            add(LocalDate.parse("2022-11-28"));
            add(LocalDate.parse("2022-11-29"));
        }};
        tripResponse.setDates(dates);
        tripResponse.getSchedule().setPeriod(period);

        doReturn(getBus2()).when(busDao).findByKey(dtoRequest.getBusName());
        Trip trip = tripMapper.toModel(dtoRequest);
        trip.setId(2);
        TripAdminDtoResponse response = tripMapper.toDTOAdmin(trip);
        assertEquals(tripResponse, response);
    }

    @Test
    void testTripScheduleDayMapping() {
        String period = "3,28";
        TripDtoRequest dtoRequest = getTripRequest2();
        dtoRequest.getSchedule().setPeriod(period);

        TripAdminDtoResponse tripResponse = getTripResponse2();
        List<LocalDate> dates = new ArrayList<>() {{
            add(LocalDate.parse("2022-11-28"));
        }};
        tripResponse.setDates(dates);
        tripResponse.getSchedule().setPeriod(period);

        doReturn(getBus2()).when(busDao).findByKey(dtoRequest.getBusName());
        Trip trip = tripMapper.toModel(dtoRequest);
        trip.setId(2);
        TripAdminDtoResponse response = tripMapper.toDTOAdmin(trip);
        assertEquals(tripResponse, response);
    }
}
