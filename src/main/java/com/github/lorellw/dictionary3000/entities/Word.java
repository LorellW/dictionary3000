package com.github.lorellw.dictionary3000.entities;

import com.github.lorellw.dictionary3000.enums.Languages;
import com.github.lorellw.dictionary3000.enums.Status;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "words")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String wordRu;
    private String wordEn;

    @Transient
    private boolean ruTranslated;
    @Transient
    private boolean enTranslated;
    @Transient
    private boolean competently;
    @Transient
    private boolean listened;

    @OneToMany(mappedBy = "word")
    private Set<UserWords> userWords;


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
