package com.github.lorellw.dictionary3000.views;

import com.github.lorellw.dictionary3000.entities.Languages;
import com.github.lorellw.dictionary3000.entities.Word;
import com.github.lorellw.dictionary3000.services.WordService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.*;

@PageTitle("Words from characters")
@Route(value = "wordsfromchars", layout = MainLayout.class)
public class WordFromLettersView extends VerticalLayout {
    private final WordService wordService;

    private TextField wordRu = new TextField("RuWord");
    private TextField inputField = new TextField("Input");
    private Button nextButton = new Button("Start");
    private Button checkButton = new Button("Check");
    private HorizontalLayout buttonField = new HorizontalLayout();

    private List<Word> wordList;
    private String currentString; //current word.wordEN
    private int index;

    public WordFromLettersView(WordService wordService) {
        this.wordService = wordService;

        configWordList();
        configTextFields();
        configNextButton();
        configCheckButton();

        add(wordRu, inputField, buttonField, new HorizontalLayout(nextButton, checkButton));

        setAlignItems(Alignment.CENTER);
    }

    private Collection<Component> configButtonField() {
        List<String> characters = Arrays.asList(getCurrentString().split(""));
        Collections.shuffle(characters);
        List<Component> buttons = new ArrayList<>();
        characters.forEach(s -> buttons.add(configCharacterButton(s)));

        return buttons;
    }

    private String getCurrentString() {
        if (currentString.contains("(")) {
            return currentString.substring(0, currentString.indexOf("(")).trim();
        } else {
            return currentString;
        }
    }

    private void configWordList() {
        wordList = wordService.getUnwritten();
        Collections.shuffle(wordList);
    }

    private void configNextButton() {
        nextButton.addClickShortcut(Key.SPACE);
        nextButton.addClickListener(buttonClickEvent -> {
            nextButton.setText("Next");
            nextButton.setEnabled(false);

            currentString = wordList.get(index).getWordEn();
            wordRu.setValue(wordList.get(index).getWordRu());

            inputField.setValue("");
            inputField.setPrefixComponent(null);

            buttonField.removeAll();
            buttonField.add(configButtonField());
            index++;
        });

    }

    private Button configCharacterButton(String letter) {
        Button button = new Button();
        button.setMinWidth("3em");
        button.setText(letter);
        button.addClickListener(buttonClickEvent -> {
            inputField.setValue(inputField.getValue() + button.getText());
            buttonClickEvent.getSource().setEnabled(false);
        });
        return button;
    }

    private void configCheckButton() {
        checkButton.addClickShortcut(Key.ENTER);

        checkButton.addClickListener(buttonClickEvent -> {
            if (inputField.getValue().equals(currentString)) {
                inputField.setPrefixComponent(VaadinIcon.CHECK.create());
                wordList.get(index - 1).setCompetently(true);
                wordService.saveWord(wordList.get(index-1));
            } else {
                inputField.setPrefixComponent(VaadinIcon.BAN.create());
            }
            buttonField.getChildren().forEach(component -> ((Button)component).setEnabled(false));
            nextButton.setEnabled(true);
        });
    }

    private void configTextFields() {
        wordRu.setReadOnly(true);
        wordRu.getStyle().set("width", "21em");
        inputField.setReadOnly(true);
        inputField.getStyle().set("width", "21em");
    }

}
