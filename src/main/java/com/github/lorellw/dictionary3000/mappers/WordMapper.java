package com.github.lorellw.dictionary3000.mappers;


import com.github.lorellw.dictionary3000.entities.User;
import com.github.lorellw.dictionary3000.entities.UserWords;
import com.github.lorellw.dictionary3000.entities.Word;
import com.github.lorellw.dictionary3000.enums.Languages;
import org.springframework.stereotype.Component;

@Component
public class WordMapper {

    public Word toWord(UserWords userWords){
        Word temp = userWords.getWord();

        temp.setTranslated(Languages.ruRU, userWords.isRuTranslated());
        temp.setTranslated(Languages.enEN, userWords.isEnTranslated());
        temp.setCompetently(userWords.isCompetently());

        return temp;
    }

    public UserWords toUserWords(Word word, User user){
        UserWords temp = new UserWords();

        temp.setUser(user);
        temp.setWord(word);
        temp.setTranslated(Languages.ruRU, word.isRuTranslated());
        temp.setTranslated(Languages.enEN, word.isEnTranslated());
        temp.setCompetently(word.isCompetently());

        return temp;
    }
}
