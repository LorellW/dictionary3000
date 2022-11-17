package com.github.lorellw.dictionary3000.services;

import com.github.lorellw.dictionary3000.entities.Languages;
import com.github.lorellw.dictionary3000.entities.Word;
import com.github.lorellw.dictionary3000.repositories.WordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService {
    private final WordRepository repository;

    public WordService(WordRepository repository) {
        this.repository = repository;
    }

    public void addWord(Word word){
        if (word == null){
            System.err.println("Word is null");
            return;
        }
        repository.save(word);
    }

    public void addWords(){

    }


    public List<Word> getAll(String filter){
        if (filter == null || filter.isEmpty()) {
            return repository.findAll();
        }else{
            return repository.search(filter);
        }
    }

    public List<Word> getUnstudied(){
        return repository.searchUnstudied();
    }

    public void update(Word newWord){
        if (repository.findById(newWord.getId()).isPresent()){
            repository.save(newWord);
        }
    }

    public List<Word> getUntranslated(Languages lang){
        return null;
    }
}
