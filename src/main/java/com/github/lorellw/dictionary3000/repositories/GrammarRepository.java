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
    @Query("select g from GrammaticalTask g "+
            "where g.lesson = :value ")
    List<GrammaticalTask> findAllByLesson(@Param("value") Integer value);
    @Query("select lesson from GrammaticalTask")
    Set<Integer> getMaxLesson();
}
