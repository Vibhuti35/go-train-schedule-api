package com.example.service;

import com.example.model.GoTrainSchedule;
import com.example.repository.GoTrainScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
@Service
public class GoTrainScheduleService {

    @Autowired
    private GoTrainScheduleRepository goTrainScheduleRepository;

    private final Pattern timePattern12Hour = Pattern.compile("(1[0-2]|0?[1-9]):([0-5][0-9])(am|pm)");

    private final Pattern timePattern24Hour = Pattern.compile("(2[0-3]|[01]?[0-9])([0-5][0-9])");

    private final SimpleDateFormat dbTimeFormatter = new SimpleDateFormat("HHmm");

    private final SimpleDateFormat inputTimeFormatter = new SimpleDateFormat("hh:mmaaa", Locale.US);

    private static final String IDENTIFIER_12HOUR = "m";

    public List<GoTrainSchedule> getFullSchedule() {

        return goTrainScheduleRepository.findAll();
    }

    public List<GoTrainSchedule> getScheduleByLineAndDeparture(String line, String departure) {

        //if departure time is not provided in the request, then search by line else by line and departure time
        if(departure == null) {
            List<GoTrainSchedule> trainSchedulesByLine =  goTrainScheduleRepository.findByLine(line);

            //If line/train does not exist throw 404 else return matching train schedule
            if(CollectionUtils.isEmpty(trainSchedulesByLine)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Line does not exist");
            }
            else {
                return trainSchedulesByLine;
            }
        } else {

            validateDepartureTime(departure);

            //check if input is 12-hour format then convert to 24 hour else just convert to integer
            List<GoTrainSchedule> trainSchedulesByLineAndDeparture = goTrainScheduleRepository.findByLineAndDeparture(
                    line, departure.contains(IDENTIFIER_12HOUR) ? formatDepartureTime(departure) : Integer.parseInt(departure));

            return trainSchedulesByLineAndDeparture;
        }
    }

    //To validate 12-hour format and 24-hour format, throw bad request if not valid
    //Can be moved to separate validator class
    private void validateDepartureTime(String departure) {

        if(!timePattern12Hour.matcher(departure).matches() && !timePattern24Hour.matcher(departure).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departure time is not valid, expected formats for ex. 1755/05:55pm");
        }
    }

    //Format timing and convert to integer if the input is 12-hour format departure time
    private Integer formatDepartureTime(String departure) {

        try {
            return Integer.parseInt(
                    dbTimeFormatter.format(inputTimeFormatter.parse(departure)));
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
