package net.thumbtack.buscompany.mappers;

import net.thumbtack.buscompany.dao.BusDao;
import net.thumbtack.buscompany.dto.trip.ScheduleDtoRequest;
import net.thumbtack.buscompany.models.TripDate;
import net.thumbtack.buscompany.services.TranslitToLatin;
import net.thumbtack.buscompany.dto.trip.TripClientDtoResponse;
import net.thumbtack.buscompany.dto.trip.TripDtoRequest;
import net.thumbtack.buscompany.dto.trip.TripAdminDtoResponse;
import net.thumbtack.buscompany.models.Trip;
import org.mapstruct.*;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring", uses = {BusDao.class, BusMapper.class, TranslitToLatin.class})
public abstract class TripMapper {
    private LocalDate date;
    private LocalDate toDate;

    @Mapping(target = "bus", source = "busName", qualifiedByName = "getBus")
    @Mapping(target = "approved", constant = "false")
    @Mapping(target = "dates", ignore = true)
    public abstract Trip toModel(TripDtoRequest dto);

    @InheritConfiguration(name = "toDTOClient")
    public abstract TripAdminDtoResponse toDTOAdmin(Trip model);

    @Mapping(target = "fromStation", qualifiedByName = "transliterate")
    @Mapping(target = "toStation", qualifiedByName = "transliterate")
    @Mapping(target = "dates", source = "model", qualifiedByName = "getLocalDates")
    public abstract TripClientDtoResponse toDTOClient(Trip model);

    @Mapping(target = "dates", ignore = true)
    public abstract Trip update(TripDtoRequest dto, @MappingTarget Trip trip);

    @Named("getLocalDates")
    public List<LocalDate> getLocalDates(Trip trip) {
        List<TripDate> tripDates = trip.getDates();
        List<LocalDate> dates = new ArrayList<>();
        tripDates.forEach(tripDate -> dates.add(tripDate.getDate()));
        return dates;
    }

    @AfterMapping
    public void getTripDates(TripDtoRequest dto, @MappingTarget Trip model) {
        List<LocalDate> dates = getLocalDates(dto);
        List<TripDate> tripDates = new ArrayList<>();
        dates.forEach(date -> tripDates.add(new TripDate(date)));
        model.setDates(tripDates);
    }

    public List<LocalDate> getLocalDates(TripDtoRequest trip) {
        ScheduleDtoRequest schedule = trip.getSchedule();
        if (schedule == null) {
            return trip.getDates();
        }
        date = schedule.getFromDate();
        toDate = schedule.getToDate();
        String period = schedule.getPeriod();

        char firstLetter = period.charAt(0);
        if (Character.isUpperCase(firstLetter)) {
            return getDatesByWeekDays(period);
        }
        if (Character.isDigit(firstLetter)) {
            return getFromDays(period);
        }

        switch (period) {
            case ("daily") : return nextDay(date, 1);
            case ("odd") : {
                if (isEvenDay(date)) date = date.plusDays(1);
                return nextDay(date, 2);
            }
            case ("even") : {
                if (!isEvenDay(date)) date = date.plusDays(1);
                return nextDay(date, 2);
            }
        }
        return new ArrayList<>();
    }

    private List<LocalDate> getDatesByWeekDays(String period) {
        List<LocalDate> dates = new ArrayList<>();
        DayOfWeek[] weekDays = Arrays.stream(period.split(",")).map(DayOfWeek::valueOf).toArray(DayOfWeek[]::new);
        for (var day : weekDays) {
            LocalDate newDate = date.with(day);
            if (newDate.compareTo(date) < 0) {
                date = newDate.plusDays(7);
            }
            dates.addAll(nextDay(newDate, 7));
        }
        return dates;
    }

    private List<LocalDate> nextDay(LocalDate date, int dayCount) {
        List<LocalDate> dates = new ArrayList<>();
        while (date.compareTo(toDate) <= 0) {
            dates.add(date);
            date = date.plusDays(dayCount);
        }
        return dates;
    }

    private List<LocalDate> getFromDays(String period) {
        List<LocalDate> dates = new ArrayList<>();
        String[] days = period.split(",");
        while (date.compareTo(toDate) <= 0) {
            for (String day : days) {
                try {
                    LocalDate newDate = date.withDayOfMonth(Integer.parseInt(day));
                    if (newDate.compareTo(toDate) >= 0) return dates;
                    if (newDate.compareTo(date) >= 0) {
                        dates.add(newDate);
                    }
                } catch (DateTimeException ignored) {
                }
            }
            date = date.with(TemporalAdjusters.firstDayOfNextMonth());
        }
        return dates;
    }

    public boolean isEvenDay(LocalDate date) {
        return date.getDayOfMonth() % 2 == 0;
    }

}