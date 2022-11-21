package com.github.lorellw.dictionary3000.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "words")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String wordRu;
    private String wordEn;
    private int progress;

    @Column(name = "ru_translated")
    private boolean isRuTranslated;
    @Column(name = "en_translated")
    private boolean isEnTranslated;

    public void setTranslated(Languages lang, boolean translated){
        if (lang == Languages.enEN){
            isEnTranslated = translated;
        }
        if (lang == Languages.ruRU){
            isRuTranslated = translated;
        }
    }
}
