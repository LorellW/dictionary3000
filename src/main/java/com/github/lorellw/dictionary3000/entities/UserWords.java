package com.github.lorellw.dictionary3000.entities;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_words")
public class UserWords {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "id_word")
    private Word word;

    @Column(name = "ru_translated")
    private boolean ruTranslated;
    @Column(name = "en_translated")
    private boolean enTranslated;
    @Column(name = "competently")
    private boolean competently;
}
