package com.github.lorellw.dictionary3000.repositories;

import com.github.lorellw.dictionary3000.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface LessonRepository extends JpaRepository<Lesson,Integer> {
    @Query("select id from Lesson")
    Set<Integer> getLessonNumbers();
}
