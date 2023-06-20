package com.github.lorellw.dictionary3000.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "exercises")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "text")
    private String text;
    @Column(name = "seq_num")
    private Integer seqNum;
    @ManyToOne
    @JoinColumn(name = "id_lesson")
    private Lesson lesson;

    @OneToMany(mappedBy = "exercise")
    private Set<GrammaticalTask> tasks;

}
