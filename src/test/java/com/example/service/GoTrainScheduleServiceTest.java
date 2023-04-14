package com.example.service;

import com.example.model.GoTrainSchedule;
import com.example.repository.GoTrainScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GoTrainScheduleServiceTest {
    @InjectMocks
    private GoTrainScheduleService goTrainScheduleService;
    @Mock
    private GoTrainScheduleRepository goTrainScheduleRepository;

    @Test
    void getAllTrainSchedule() {

        List<GoTrainSchedule> schedules = getGoTrainSchedules();

        when(goTrainScheduleRepository.findAll()).thenReturn(schedules);
        List<GoTrainSchedule> fullSchedule = goTrainScheduleService.getFullSchedule();
        assertEquals(schedules,schedules);
        assertEquals(schedules.size(),2);
    }

    @Test
    void getGoTrainScheduleByLine() {

        List<GoTrainSchedule> schedules = getGoTrainSchedules();
        schedules.remove(1);

        when(goTrainScheduleRepository.findByLine("Lakeshore")).thenReturn(schedules);
        List<GoTrainSchedule> fullSchedule = goTrainScheduleService.getScheduleByLineAndDeparture("Lakeshore", null);
        assertEquals(schedules,schedules);
        assertEquals(schedules.size(),1);
    }

    @Test
    void getGoTrainScheduleByLineAndDeparture24Hour() {

        List<GoTrainSchedule> schedules = getGoTrainSchedules();
        schedules.remove(1);

        when(goTrainScheduleRepository.findByLineAndDeparture("Lakeshore", 1125)).thenReturn(schedules);
        List<GoTrainSchedule> fullSchedule = goTrainScheduleService.getScheduleByLineAndDeparture("Lakeshore", "1125");
        assertEquals(schedules,schedules);
        assertEquals(schedules.size(),1);
    }

    @Test
    void getGoTrainScheduleByLineAndDeparture12Hour() {

        List<GoTrainSchedule> schedules = getGoTrainSchedules();
        schedules.remove(1);

        when(goTrainScheduleRepository.findByLineAndDeparture("Lakeshore", 1125)).thenReturn(schedules);
        List<GoTrainSchedule> fullSchedule = goTrainScheduleService.getScheduleByLineAndDeparture("Lakeshore", "11:25am");
        assertEquals(schedules,schedules);
        assertEquals(schedules.size(),1);
    }

    @Test
    void getGoTrainScheduleByLineAndEmptyDeparture() {

        when(goTrainScheduleRepository.findByLineAndDeparture("Lakeshore", 1125)).thenReturn(Collections.emptyList());
        assertThrows(ResponseStatusException.class, () -> {
            goTrainScheduleService.getScheduleByLineAndDeparture("Lakeshore", null);
        });
    }

    @Test
    void getGoTrainScheduleByLineAndInvalidDeparture12hour() {

        assertThrows(ResponseStatusException.class, () -> {
            goTrainScheduleService.getScheduleByLineAndDeparture("Lakeshore", "15:00aam");
        });
    }

    @Test
    void getGoTrainScheduleByLineAndInvalidDeparture24hour() {

        assertThrows(ResponseStatusException.class, () -> {
            goTrainScheduleService.getScheduleByLineAndDeparture("Lakeshore", "2600");
        });
    }
    private static List<GoTrainSchedule> getGoTrainSchedules() {

        List<GoTrainSchedule> schedules = new ArrayList<>();
        GoTrainSchedule goTrainSchedule1 = new GoTrainSchedule();
        goTrainSchedule1.setLine("Lakeshore");
        goTrainSchedule1.setId(1);
        goTrainSchedule1.setArrival(1120);
        goTrainSchedule1.setDeparture(1125);
        schedules.add(goTrainSchedule1);

        GoTrainSchedule goTrainSchedule2 = new GoTrainSchedule();
        goTrainSchedule2.setLine("Barrie");
        goTrainSchedule2.setId(2);
        goTrainSchedule2.setArrival(1120);
        goTrainSchedule2.setDeparture(1125);
        schedules.add(goTrainSchedule2);

        return schedules;
    }
}
