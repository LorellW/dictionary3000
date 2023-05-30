package com.github.lorellw.dictionary3000.services;

import com.github.lorellw.dictionary3000.repositories.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LessonService {
    private final LessonRepository repository;

    public LessonService(LessonRepository repository) {
        this.repository = repository;
    }

    public Set<Integer> getAllLessons() {
        return repository.getLessonNumbers();
    }
}
