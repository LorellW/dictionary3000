package com.github.lorellw.dictionary3000.services;

import com.github.lorellw.dictionary3000.entities.GrammaticalTask;
import com.github.lorellw.dictionary3000.repositories.GrammarRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GrammarService {
    private final GrammarRepository repository;


    public GrammarService(GrammarRepository repository) {
        this.repository = repository;
    }

    public void saveTask(GrammaticalTask task){
        if (task == null){
            System.err.println("Task is null");
            return;
        }
        repository.save(task);
    }

    public List<GrammaticalTask> getTasks(){
        return repository.findAll();
    }


    public List<String> getTasksTextByLesson(Integer lesson, Integer exercise) {
        List<String> texts = new ArrayList<>();
        repository.findAllByLesson(lesson,exercise).forEach(task -> {
            texts.add(task.getTask());
        });
        return texts;
    }


    public Map<String,String> getWrongAnswers(List<String> answers, Integer lesson, Integer exercise) {

        Map<String,String> wrongAnswers = new HashMap<>();
        int[] i = new int[]{0};
        repository.findAllByLesson(lesson, exercise).forEach(task -> {
            System.out.println(task.getId());
            if (!task.getSolution().equals(answers.get(i[0]))) {
                wrongAnswers.put(i[0] + ". " + task.getSolution(), answers.get(i[0]));
            }
            i[0]++;
        });
        return wrongAnswers;
    }

}
