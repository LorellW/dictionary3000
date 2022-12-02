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
    }
}
