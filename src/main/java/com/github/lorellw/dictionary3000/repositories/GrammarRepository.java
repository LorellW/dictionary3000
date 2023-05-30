package com.github.lorellw.dictionary3000.repositories;

import com.github.lorellw.dictionary3000.entities.GrammaticalTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GrammarRepository extends JpaRepository<GrammaticalTask,Long> {

    //TODO This is right method
    @Query("select g from GrammaticalTask g "+
            "join Exercise e on g.exercise=e.id "+
            "join Lesson l on e.lesson=l.id "+
            "where l.id = :lesson "+
            "and "+
            "e.seqNum = :exercise")
    Set<GrammaticalTask> findAllByLesson(@Param("lesson") Integer lesson,
                                 @Param("exercise") Integer exercise);
}
