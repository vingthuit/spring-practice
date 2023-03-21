package net.thumbtack.buscompany.security.validators;

import net.thumbtack.buscompany.dao.BusDao;
import net.thumbtack.buscompany.dao.TripDao;
import net.thumbtack.buscompany.models.TripDate;
import net.thumbtack.buscompany.security.exceptions.ErrorCode;
import net.thumbtack.buscompany.security.exceptions.TripException;
import net.thumbtack.buscompany.models.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TripValidator {
    @Autowired
    private TripDao tripDao;
    @Autowired
    private BusDao busDao;

    public void busIsValid(String busName) {
        if (busDao.findByKey(busName) == null) {
            throw new TripException(ErrorCode.WRONG_BUS);
        }
    }

    public Trip isValid(int tripId) {
        Trip trip = tripDao.findByKey(tripId);
        if (trip == null) {
            throw new TripException(ErrorCode.NONEXISTENT_TRIP_ID);
        }
        return trip;
    }

    public void isApproved(int tripId) {
        Trip trip = isValid(tripId);
        if (trip == null) {
            throw new TripException(ErrorCode.NOT_APPROVED);
        }
    }

    public TripDate isValid(int tripId, LocalDate date) {
        Trip trip = isValid(tripId);
        return trip.getDates().stream()
                .filter(t -> t.getDate().equals(date))
                .findFirst().orElseThrow(() -> new TripException(ErrorCode.NONEXISTENT_DATE));
    }

}
