package com.github.lorellw.dictionary3000.services;

import com.github.lorellw.dictionary3000.entities.User;
import com.github.lorellw.dictionary3000.entities.UserWords;
import com.github.lorellw.dictionary3000.entities.Word;
import com.github.lorellw.dictionary3000.enums.Languages;
import com.github.lorellw.dictionary3000.enums.Status;
import com.github.lorellw.dictionary3000.mappers.WordMapper;
import com.github.lorellw.dictionary3000.repositories.UserWordsRepository;
import com.github.lorellw.dictionary3000.repositories.WordRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserWordsService {
    private final UserWordsRepository userWordsRepository;
    private final WordRepository wordRepository;
    private final SecurityService securityService;
    private final WordMapper mapper;

    public UserWordsService(UserWordsRepository userWordsRepository,
                            WordRepository wordRepository,
                            SecurityService securityService,
                            WordMapper mapper) {
        this.userWordsRepository = userWordsRepository;
        this.wordRepository = wordRepository;
        this.securityService = securityService;
        this.mapper = mapper;
    }


    public void update(Word learnedWord) {
        userWordsRepository.findByWord(learnedWord).forEach(userWords -> {
            if (userWords.getUser().getId().equals(((User) securityService.getAuthenticatedUser()).getId())){
                userWords.setWord(learnedWord);
                Long tempId = userWords.getId();
                userWords = mapper.toUserWords(learnedWord, userWords.getUser());
                userWords.setId(tempId);
                userWordsRepository.save(userWords);
            }
        });
    }

    public void saveWord(Word word) {
        if (word == null) {
            System.err.println("Word is null");
            return;
        }
        if (wordRepository.findByWordEn(word.getWordEn()).isEmpty()){
            wordRepository.save(word);
        }
        if (userWordsRepository.findByWord(word).isEmpty()){
            userWordsRepository.save(mapper.toUserWords(word, (User) securityService.getAuthenticatedUser()));
        }
    }

    public List<Word> getUntranslated(Languages lang) {

        List<Word> temp = new ArrayList<>();
        if (lang == Languages.enEN) {
            userWordsRepository.searchEnUntranslated((User) securityService.getAuthenticatedUser()).forEach(userWord -> {
                temp.add(mapper.toWord(userWord));
            });
        } else if (lang == Languages.ruRU) {
            userWordsRepository.searchRuUntranslated((User) securityService.getAuthenticatedUser()).forEach(userWord -> {
                temp.add(mapper.toWord(userWord));
            });
        } else {
            userWordsRepository.searchUntranslated((User) securityService.getAuthenticatedUser()).forEach(userWord -> {
                temp.add(mapper.toWord(userWord));
            });
        }
        return temp;
    }


    public List<Word> getAll(String filter, Status status) {
        List<Word> words = new ArrayList<>();
        if ((filter == null || filter.isEmpty()) && status == null) {
            userWordsRepository.findByUser((User) securityService.getAuthenticatedUser()).forEach(userWord -> {
                words.add(mapper.toWord(userWord));
            });
        } else if (status != null) {
            userWordsRepository.search(/*filter,*/ (User) securityService.getAuthenticatedUser()).forEach(userWord -> {
                Word temp = mapper.toWord(userWord);
                if (temp.getStatus() == status) {
                    words.add(temp);
                }
            });
        } else {
            userWordsRepository.search(/*filter,*/ (User) securityService.getAuthenticatedUser()).forEach(userWord -> {
                words.add(mapper.toWord(userWord));
            });
        }
        return words;
    }

    public void addWords() {
        User user = (User) securityService.getAuthenticatedUser();
        List<Word> words = wordRepository.findAll();


        words.forEach(word -> {
            UserWords temp = new UserWords();
            temp.setUser(user);
            temp.setCompetently(false);
            temp.setTranslated(Languages.ALL, false);
            temp.setWord(word);
            userWordsRepository.save(temp);
        });
    }

    public List<Word> getUnwritten() {
        List<Word> words = new ArrayList<>();
        userWordsRepository.searchUnwritten((User) securityService.getAuthenticatedUser()).forEach(userWord -> {
            words.add(mapper.toWord(userWord));
        });
        return words;
    }
}
