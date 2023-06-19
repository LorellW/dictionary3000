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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String task;
    private String solution;

    @ManyToOne
    @JoinColumn(name = "id_exe")
    private Exercise exercise;
}
