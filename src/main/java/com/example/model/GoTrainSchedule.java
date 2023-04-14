package com.example.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="GOTRAINSCHEDULE")
@Data
@Getter
@Setter
public class GoTrainSchedule {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @Column
    private String line;
    @Column
    private Integer departure;
    @Column
    private Integer arrival;
}
