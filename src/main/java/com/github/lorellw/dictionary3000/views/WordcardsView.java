package com.github.lorellw.dictionary3000.views;

import com.github.lorellw.dictionary3000.entities.Languages;
import com.github.lorellw.dictionary3000.entities.Word;
import com.github.lorellw.dictionary3000.services.WordService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.Collections;
import java.util.List;

@Route(value = "wordcards",layout = MainLayout.class)
public class WordcardsView extends VerticalLayout {

    private final WordService wordService;
    private List<Word> wordList;

    private TextField enWord = new TextField();
    private TextField ruWord = new TextField();
    private int index = 0;

    public WordcardsView(WordService wordService) {
        this.wordService = wordService;
        wordList = wordService.getUnstudied(Languages.ALL);
        Collections.shuffle(wordList);
        add(addTextFields(),createButtonField());
    }

    private HorizontalLayout addTextFields(){
        enWord.setLabel("EN");
        enWord.setReadOnly(true);
        ruWord.setLabel("RU");
        ruWord.setReadOnly(true);
        HorizontalLayout textFields = new HorizontalLayout();
        textFields.setWidth("100%");
        textFields.add(enWord,ruWord);
        return textFields;
    }


    private HorizontalLayout createButtonField(){

        Button knowButton = new Button("Know");
        Button dontKnowButton = new Button("Don't know");
        Button nextButton = new Button("Start");

        knowButton.addClickShortcut(Key.ARROW_LEFT);
        knowButton.addClickListener(buttonClickEvent -> {
            Word learnedWord = wordList.get(index-1);
            ruWord.setValue(learnedWord.getWordRu());
            learnedWord.setEnTranslated(true);
            learnedWord.setRuTranslated(true);
            wordService.update(learnedWord);
        });
        dontKnowButton.addClickShortcut(Key.ARROW_RIGHT);
        dontKnowButton.addClickListener(buttonClickEvent -> {
            Word unlearnedWord = wordList.get(index-1);
            ruWord.setValue(unlearnedWord.getWordRu());
            if (unlearnedWord.isEnTranslated() || unlearnedWord.isRuTranslated()){
                unlearnedWord.setEnTranslated(false);
                unlearnedWord.setEnTranslated(false);
                wordService.update(unlearnedWord);
            }
        });
        nextButton.addClickShortcut(Key.SPACE);
        nextButton.addClickListener(buttonClickEvent -> {
            nextButton.setText("Next");
            ruWord.setValue("");
            enWord.setValue(wordList.get(index).getWordEn());
            index++;
        });
        HorizontalLayout buttonField = new HorizontalLayout();
        buttonField.add(knowButton,nextButton,dontKnowButton);
        return buttonField;
    }

}
