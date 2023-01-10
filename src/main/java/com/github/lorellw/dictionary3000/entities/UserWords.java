package com.github.lorellw.dictionary3000.entities;


import com.github.lorellw.dictionary3000.enums.Languages;
import com.github.lorellw.dictionary3000.enums.Status;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_words")
public class UserWords {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "id_user")
    private User user;
    @ManyToOne()
    @JoinColumn(name = "id_word")
    private Word word;

    @Column(name = "ru_translated")
    private boolean ruTranslated;
    @Column(name = "en_translated")
    private boolean enTranslated;
    @Column(name = "competently")
    private boolean competently;

    public void setTranslated(Languages lang, boolean translated){
        if (lang == Languages.enEN){
            enTranslated = translated;
        }
        if (lang == Languages.ruRU){
            ruTranslated = translated;
        }
        if (lang == Languages.ALL){
            enTranslated = translated;
            ruTranslated = translated;
        }
    }

    public Status getStatus(){
        if (!isRuTranslated() && !isEnTranslated() && !isCompetently()) {
            return Status.NEW;
        }
        if (!isRuTranslated() || !isEnTranslated() || !isCompetently()) {
            return Status.ONSTUDY;
        }
        if (isRuTranslated() && isEnTranslated() && isCompetently()) {
            return Status.STUDIED;
        }
        else {
            return Status.BROKEN;
        }
    }
}
