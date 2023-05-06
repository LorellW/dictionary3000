package com.github.lorellw.dictionary3000.views;

import com.github.lorellw.dictionary3000.enums.Languages;
import com.github.lorellw.dictionary3000.entities.Word;
import com.github.lorellw.dictionary3000.services.UserWordsService;
import com.github.lorellw.dictionary3000.services.WordService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;
import java.util.List;

@PageTitle("Word cards")
@Route(value = "wordcards",layout = MainLayout.class)
public class WordcardsView extends AbstractView {

    private final UserWordsService userWordsService;
    private List<Word> wordList;

    private TextField enWord = new TextField();
    private TextField ruWord = new TextField();
    private int index = 0;

    public WordcardsView(UserWordsService userWordsService) {
        this.userWordsService = userWordsService;
        wordList = userWordsService.getUntranslated(Languages.ALL);
        Collections.shuffle(wordList);
        add(createTextFields(),createButtonField());
        setAlignItems(Alignment.CENTER);
    }

    private HorizontalLayout createTextFields(){
        enWord.setLabel("EN");
        enWord.setReadOnly(true);
        ruWord.setLabel("RU");
        ruWord.setReadOnly(true);
        HorizontalLayout textFields = new HorizontalLayout();
        textFields.add(enWord,ruWord);
        return textFields;
    }

    private HorizontalLayout createButtonField(){

        Button knowButton = new Button("Know");
        knowButton.setWidth("7em");
        Button dontKnowButton = new Button("Don't know");
        dontKnowButton.setWidth("7em");
        Button nextButton = new Button("Start");
        nextButton.setWidth("7em");

        knowButton.addClickShortcut(Key.ARROW_LEFT);
        knowButton.addClickListener(buttonClickEvent -> {
            Word learnedWord = wordList.get(index-1);
            ruWord.setValue(learnedWord.getWordRu());
            learnedWord.setTranslated(Languages.ALL,true);
            learnedWord.setCompetently(true);
            userWordsService.update(learnedWord);
        });
        dontKnowButton.addClickShortcut(Key.ARROW_RIGHT);
        dontKnowButton.addClickListener(buttonClickEvent -> {
            Word unlearnedWord = wordList.get(index-1);
            ruWord.setValue(unlearnedWord.getWordRu());
            if (unlearnedWord.isEnTranslated() || unlearnedWord.isRuTranslated() || unlearnedWord.isCompetently() ){
                unlearnedWord.setTranslated(Languages.ALL,false);
                unlearnedWord.setCompetently(false);
                userWordsService.update(unlearnedWord);
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
