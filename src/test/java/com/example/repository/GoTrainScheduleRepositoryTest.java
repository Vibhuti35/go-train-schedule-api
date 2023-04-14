package com.example.repository;

import com.example.model.GoTrainSchedule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class GoTrainScheduleRepositoryTest {
    @Autowired
    private GoTrainScheduleRepository goTrainScheduleRepository;

    @Test
    void getAllTrainSchedule() {

        List<GoTrainSchedule> allSchedules = goTrainScheduleRepository.findAll();
        assertNotNull(allSchedules);
        assertEquals(allSchedules.size(),17);
    }

    @Test
    void getAllTrainScheduleByLine() {

        List<GoTrainSchedule> allSchedules = goTrainScheduleRepository.findByLine("Lakeshore");
        assertNotNull(allSchedules);
        assertEquals(allSchedules.size(),5);
    }

    @Test
    void getAllTrainScheduleByLineAndDeparture() {

        List<GoTrainSchedule> allSchedules = goTrainScheduleRepository.findByLineAndDeparture("Lakeshore", 1600);
        assertNotNull(allSchedules);
        assertEquals(allSchedules.size(), 1);
    }

    @Test
    void getAllTrainScheduleByLineAndDepartureDoesNotExist() {

        List<GoTrainSchedule> allSchedules = goTrainScheduleRepository.findByLineAndDeparture("test", null);
        assertNotNull(allSchedules);
        assertEquals(allSchedules.size(), 0);
    }
}
