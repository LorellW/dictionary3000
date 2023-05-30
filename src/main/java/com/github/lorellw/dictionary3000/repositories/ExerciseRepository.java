package com.github.lorellw.dictionary3000.repositories;

import com.github.lorellw.dictionary3000.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ExerciseRepository extends JpaRepository<Exercise,Integer> {

//    @Query("select seqNum from Exercise e "+
//            "join Lesson l on e.lesson=l.id " +
//            "where l.id = :value")
    @Query("select e from Exercise e "+
            "join Lesson l on e.lesson=l.id "+
            "where l.id = :value")
    Set<Exercise> getExerciseNumbers(@Param("value") Integer value);
}
