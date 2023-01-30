package com.github.lorellw.dictionary3000.views;

import com.github.lorellw.dictionary3000.entities.UserWords;
import com.github.lorellw.dictionary3000.entities.Word;
import com.github.lorellw.dictionary3000.services.UserWordsService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@PageTitle("About")
@Route(value = "", layout = MainLayout.class)
public class AboutView extends AbstractView {

    private final UserWordsService userWordsService;

    public AboutView(UserWordsService userWordsService) {
        this.userWordsService = userWordsService;

        Button addAllButton = new Button();
        addAllButton.addClickListener(buttonClickEvent -> {
            //userWordsService.addWords();
        });
        add(addAllButton);
    }
}
