package com.example.resource;

import com.example.model.GoTrainSchedule;
import com.example.service.GoTrainScheduleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GoTrainScheduleResourceTest {
    @InjectMocks
    private GoTrainScheduleResource goTrainScheduleResource;
    @Mock
    private GoTrainScheduleService goTrainScheduleService;

    @Test
    void getAllLineScheduleTest() {

        List<GoTrainSchedule> schedules = getGoTrainSchedules();

        when(goTrainScheduleService.getFullSchedule()).thenReturn(schedules);
        ResponseEntity<List<GoTrainSchedule>> goTrainSchedule = goTrainScheduleResource.getGoTrainSchedule();
        assertEquals(goTrainSchedule.getStatusCode(), HttpStatus.OK);
        assertEquals(goTrainSchedule.getBody(),schedules);
        assertEquals(goTrainSchedule.getBody().size(),2);
    }

    @Test
    void getScheduleByLineTest() {

        List<GoTrainSchedule> schedules = getGoTrainSchedules();
        schedules.remove(1);

        when(goTrainScheduleService.getScheduleByLineAndDeparture("Lakeshore", null)).thenReturn(schedules);
        ResponseEntity<List<GoTrainSchedule>> goTrainSchedule = goTrainScheduleResource.getGoTrainScheduleByLineAndDeparture("Lakeshore", null);
        assertEquals(goTrainSchedule.getStatusCode(), HttpStatus.OK);
        assertEquals(goTrainSchedule.getBody(), schedules);
        assertEquals(goTrainSchedule.getBody().size(),1);
    }

    @Test
    void getScheduleByLineAndDepartureTest() {

        List<GoTrainSchedule> schedules = getGoTrainSchedules();
        schedules.remove(1);

        when(goTrainScheduleService.getScheduleByLineAndDeparture("Lakeshore", "1125")).thenReturn(schedules);
        ResponseEntity<List<GoTrainSchedule>> goTrainSchedule = goTrainScheduleResource.getGoTrainScheduleByLineAndDeparture("Lakeshore", "1125");
        assertEquals(goTrainSchedule.getStatusCode(), HttpStatus.OK);
        assertEquals(goTrainSchedule.getBody(), schedules);
        assertEquals(goTrainSchedule.getBody().size(),1);
    }

    @Test
    void getScheduleByLineAndDepartureTest_DoesNotExist() {

        when(goTrainScheduleService.getScheduleByLineAndDeparture("Lakeshore", null)).thenReturn(Collections.emptyList());
        ResponseEntity<List<GoTrainSchedule>> goTrainSchedule = goTrainScheduleResource.getGoTrainScheduleByLineAndDeparture("Lakeshore", null);
        assertEquals(goTrainSchedule.getStatusCode(), HttpStatus.OK);
        assertEquals(goTrainSchedule.getBody(), Collections.emptyList());
        assertEquals(goTrainSchedule.getBody().size(),0);
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
