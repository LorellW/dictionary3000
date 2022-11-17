package com.github.lorellw.dictionary3000.views;

import com.github.lorellw.dictionary3000.entities.Word;
import com.github.lorellw.dictionary3000.services.WordService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "entoru",layout = MainLayout.class)
public class enToRuView extends VerticalLayout {
    private final TextField enWord = new TextField();
    private final Tabs answers = new Tabs();
    private Button nextButton = new Button("Next");

    private final WordService wordService;
    private List<Word> wordList;

    public enToRuView(WordService wordService) {
        this.wordService = wordService;
        configTabs();
        add(enWord,answers,nextButton);
    }

    private void configTabs(){
        Tab answer1 = new Tab("answer #1");
        Tab answer2 = new Tab("answer #2");
        Tab answer3 = new Tab("answer #3");
        Tab answer4 = new Tab("answer #4");

        answers.add(answer1,answer2,answer3,answer4);
        answers.setOrientation(Tabs.Orientation.VERTICAL);
        answers.addSelectedChangeListener(selectedChangeEvent -> {
            enWord.setValue(selectedChangeEvent.getSelectedTab().getLabel());
        });
    }

    private void configButton(){

    }
}
