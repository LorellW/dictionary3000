package com.github.lorellw.dictionary3000.services;

import com.github.lorellw.dictionary3000.dto.ExerciseDto;
import com.github.lorellw.dictionary3000.repositories.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ExerciseService {
    private final ExerciseRepository repository;

    public ExerciseService(ExerciseRepository repository) {
        this.repository = repository;
    }

    public Set<Integer> getAllExerciseNumbers(Integer lessonNumber) {
        Set<Integer> numbs = new HashSet<>();
        repository.getExerciseNumbers(lessonNumber).forEach(e -> numbs.add(e.getSeqNum()));
        return numbs;
    }

    public Set<ExerciseDto> getAllExercise(Integer lessonNumber){
        Set<ExerciseDto> exercises = new HashSet<>();
        repository.getExerciseNumbers(lessonNumber).forEach(e -> {
            exercises.add(new ExerciseDto(e.getSeqNum(),e.getText()));
        });
        return exercises;
    }
}
