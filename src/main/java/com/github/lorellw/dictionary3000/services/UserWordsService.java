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

    }

    public void saveWord(Word word) {
        if (word == null){
            System.err.println("Word is null");
            return;
        }
    }


    public List<Word> getAll(String filter, Status status) {
        List<Word> words = new ArrayList<>();
        if ((filter == null || filter.isEmpty()) && status == null) {
            userWordsRepository.findByUser((User) securityService.getAuthenticatedUser()).forEach(userWords -> {
                words.add(mapper.toWord(userWords));
            });
        } else if (status != null) {
            userWordsRepository.search(filter,(User) securityService.getAuthenticatedUser()).forEach(userWords -> {
                Word temp = mapper.toWord(userWords);
                if (temp.getStatus() == status){
                    words.add(temp);
                }
            });
        } else {
            userWordsRepository.search(filter,(User) securityService.getAuthenticatedUser()).forEach(userWords -> {
                words.add(mapper.toWord(userWords));
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
}
