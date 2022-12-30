package com.github.lorellw.dictionary3000.entities;

import com.github.lorellw.dictionary3000.enums.Languages;
import com.github.lorellw.dictionary3000.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "words")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String wordRu;
    private String wordEn;

    @Column(name = "ru_translated")
    private boolean ruTranslated;
    @Column(name = "en_translated")
    private boolean enTranslated;
    @Column(name = "competently")
    private boolean competently;
    @Column(name = "listened")
    private boolean listened;

    @OneToMany(mappedBy = "word", fetch = FetchType.EAGER)
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
