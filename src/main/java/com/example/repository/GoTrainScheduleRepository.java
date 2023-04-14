package com.example.repository;

import com.example.model.GoTrainSchedule;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoTrainScheduleRepository extends JpaRepository<GoTrainSchedule, Integer> {

    @Cacheable(value = "line")
    List<GoTrainSchedule> findByLine(String line);

    @Cacheable(value = "lineByDeparture")
    List<GoTrainSchedule> findByLineAndDeparture(String line, Integer departure);
}


