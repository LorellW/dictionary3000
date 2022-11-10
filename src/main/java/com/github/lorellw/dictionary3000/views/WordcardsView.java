package com.github.lorellw.dictionary3000.views;

import com.github.lorellw.dictionary3000.services.WordService;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "wordcards",layout = MainLayout.class)
public class WordcardsView extends VerticalLayout {
    private final WordService wordService;

    public WordcardsView(WordService wordService) {
        this.wordService = wordService;
    }

    private HorizontalLayout createButtons(){
        return null;
    }
}
