package com.example.resource;

import com.example.model.GoTrainSchedule;
import com.example.service.GoTrainScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/schedule")
public class GoTrainScheduleResource {

    @Autowired
    private GoTrainScheduleService goTrainScheduleService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GoTrainSchedule>> getGoTrainSchedule() {

        return ResponseEntity.ok(goTrainScheduleService.getFullSchedule());
    }

    @GetMapping(value = "/{line}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GoTrainSchedule>> getGoTrainScheduleByLineAndDeparture(@PathVariable(name = "line") String line,
                                                                                      @RequestParam(value = "departure", required = false) String departure) {

        return ResponseEntity.ok(goTrainScheduleService.getScheduleByLineAndDeparture(line, departure));
    }
}
