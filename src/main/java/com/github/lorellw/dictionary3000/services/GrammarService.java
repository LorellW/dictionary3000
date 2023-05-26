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

    public List<GrammaticalTask> getTasksByLesson(Integer value) {
        return repository.findAllByLesson(value);
    }

    public List<String> getTasksTextByLesson(Integer value) {
        List<String> texts = new ArrayList<>();
        repository.findAllByLesson(value).forEach(task -> {
            texts.add(task.getTask());
        });
        return texts;
    }

    public Set<Integer> getLessonCount() {
        return repository.getMaxLesson();
    }

    public Map<String,String> getWrongAnswers(List<String> answers, Integer value) {

        Map<String,String> wrongAnswers = new HashMap<>();
        int[] i = new int[]{0};
        repository.findAllByLesson(value).forEach(task -> {
            if (!task.getSolution().equals(answers.get(i[0]))) {
                wrongAnswers.put(task.getSolution(), answers.get(i[0]));
            }
            i[0]++;
        });
        return wrongAnswers;
    }
}
