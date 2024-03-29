package com.github.lorellw.dictionary3000.services;

import com.github.lorellw.dictionary3000.enums.Languages;
import com.github.lorellw.dictionary3000.entities.Word;
import com.github.lorellw.dictionary3000.enums.Status;
import com.github.lorellw.dictionary3000.repositories.WordRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WordService {
    private final WordRepository repository;

    public WordService(WordRepository repository) {
        this.repository = repository;
    }

    public void saveWord(Word word) {
        if (word == null) {
            System.err.println("Word is null");
            return;
        }
        repository.save(word);
    }

    public void update(Word newWord) {
        if (repository.findById(newWord.getId()).isPresent()) {
            repository.save(newWord);
        }
    }

}
