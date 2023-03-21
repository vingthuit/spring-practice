package net.thumbtack.buscompany.services;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dao.TripDao;
import net.thumbtack.buscompany.dto.trip.TripAdminDtoResponse;
import net.thumbtack.buscompany.dto.trip.TripDtoRequest;
import net.thumbtack.buscompany.dto.trip.TripClientDtoResponse;
import net.thumbtack.buscompany.mappers.TripMapper;
import net.thumbtack.buscompany.models.Trip;
import net.thumbtack.buscompany.models.TripDate;
import net.thumbtack.buscompany.models.User;
import net.thumbtack.buscompany.models.UserType;
import net.thumbtack.buscompany.security.validators.AccessValidator;
import net.thumbtack.buscompany.security.validators.TripValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TripService {
    private final AccessValidator accessValidator;
    private final TripValidator tripValidator;
    private final TripDao tripDao;
    private final TripMapper tripMapper;

    public TripAdminDtoResponse addTrip(String cookie, TripDtoRequest request) {
        accessValidator.isAdmin(cookie);
        tripValidator.busIsValid(request.getBusName());
        Trip trip = tripMapper.toModel(request);
        tripDao.insert(trip);
        return tripMapper.toDTOAdmin(trip);
    }

    public TripAdminDtoResponse updateTrip(String cookie, int tripId, TripDtoRequest request) {
        accessValidator.isAdmin(cookie);
        tripValidator.busIsValid(request.getBusName());
        tripValidator.isValid(tripId);

        Trip trip = tripMapper.toModel(request);
        tripDao.update(tripId, trip);
        return tripMapper.toDTOAdmin(trip);
    }

    public TripAdminDtoResponse approve(String cookie, int tripId) {
        accessValidator.isAdmin(cookie);
        tripValidator.isValid(tripId);

        tripDao.approve(tripId);
        return getTripResponse(tripId);
    }

    public void deleteTrip(String cookie, int tripId) {
        accessValidator.isAdmin(cookie);
        tripValidator.isValid(tripId);
        tripDao.delete(tripId);
    }

    public TripAdminDtoResponse getTripResponse(int tripId) {
        Trip trip = tripDao.findByKey(tripId);
        return tripMapper.toDTOAdmin(trip);
    }

    public TripAdminDtoResponse getTrip(String cookie, int tripId) {
        accessValidator.isAdmin(cookie);
        tripValidator.isValid(tripId);
        return getTripResponse(tripId);
    }

    public List<TripClientDtoResponse> getTripList(String cookie, Map<String, String> allParams) {
        User user = accessValidator.isValid(cookie);
        UserType userType = user.getUserType();
        if (userType.equals(UserType.CLIENT)) {
            allParams.put("approved", "true");
        }

        List<Trip> trips = tripDao.findAll();
        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            trips = trips.stream().filter(trip -> getByParam(trip, entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
        }
        return trips.stream()
                .map(Objects.requireNonNull(getTripResponse(userType)))
                .collect(Collectors.toList());
    }

    private Function<Trip, TripClientDtoResponse> getTripResponse(UserType userType) {
        switch (userType) {
            case ADMIN : return tripMapper::toDTOAdmin;
            case CLIENT : return tripMapper::toDTOClient;
        }
        return null;
    }

    public boolean getByParam(Trip trip, String key, String value) {
        switch (key) {
            case ("fromStation") : return trip.getFromStation().equals(value);
            case ("toStation") : return trip.getToStation().equals(value);
            case ("busName") : return trip.getBus().getName().equals(value);
            case ("fromDate") : {
                LocalDate date = LocalDate.parse(value);
                LocalDate fromTripDate = trip.getDates().get(0).getDate();
                return date.equals(fromTripDate) || date.isAfter(fromTripDate);
            }
            case ("toDate") : {
                LocalDate date = LocalDate.parse(value);
                List<TripDate> dates = trip.getDates();
                LocalDate toTripDate = dates.get(dates.size() - 1).getDate();
                return date.equals(toTripDate) || date.isBefore(toTripDate);
            }
            case ("approved") : return trip.isApproved();
        }
        return false;
    }

}
