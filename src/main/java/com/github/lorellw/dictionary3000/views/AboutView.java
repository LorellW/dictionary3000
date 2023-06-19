package com.github.lorellw.dictionary3000.views;

import com.github.lorellw.dictionary3000.entities.GrammaticalTask;
import com.github.lorellw.dictionary3000.services.GrammarService;
import com.github.lorellw.dictionary3000.services.UserWordsService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("About")
@Route(value = "", layout = MainLayout.class)
public class AboutView extends AbstractView {

    private final UserWordsService userWordsService;
    private final GrammarService grammarService;

    public AboutView(UserWordsService userWordsService, GrammarService grammarService) {
        this.userWordsService = userWordsService;
        this.grammarService = grammarService;


        //TODO no comments
        Button addAllButton = new Button();
        addAllButton.addClickListener(buttonClickEvent -> {

        });
        add(addAllButton);
    }
}
