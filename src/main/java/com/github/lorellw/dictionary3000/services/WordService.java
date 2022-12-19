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

    public List<Word> getAll(String filter, Status status) {
        if ((filter == null || filter.isEmpty()) && status == null) {
            return repository.findAll();
        } else if (status != null) {
            List<Word> temp = new ArrayList<>();
            repository.search(filter).forEach(word -> {
                if (word.getStatus() == status) temp.add(word);
            });
            return temp;
        } else {
            return repository.search(filter);
        }
    }

    public void update(Word newWord) {
        if (repository.findById(newWord.getId()).isPresent()) {
            repository.save(newWord);
        }
    }

    public List<Word> getUnwritten() {
        return repository.searchUnwritten();
    }

    public List<Word> getUntranslated(Languages lang) {
        if (lang == Languages.enEN) {
            return repository.searchEnUntranslated();
        } else if (lang == Languages.ruRU) {
            return repository.searchRuUntranslated();
        }
        return repository.searchUnstudied();
    }
}
