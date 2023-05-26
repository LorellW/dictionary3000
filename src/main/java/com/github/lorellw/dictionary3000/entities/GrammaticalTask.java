package com.github.lorellw.dictionary3000.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "gtasks")
public class GrammaticalTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer lesson;

    private String task;
    private String solution;

}
