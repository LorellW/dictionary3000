package com.github.lorellw.dictionary3000.views;

import com.github.lorellw.dictionary3000.entities.Word;
import com.github.lorellw.dictionary3000.services.UserWordsService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.*;

@PageTitle("Words from characters")
@Route(value = "wordsfromchars", layout = MainLayout.class)
public class WordFromLettersView extends AbstractView {
    private final UserWordsService userWordsService;

    private final TextField task = new TextField("RuWord");
    private final TextField answer = new TextField("Input");

    private final Button nextButton = new Button("Start");
    private final Button checkButton = new Button("Check");
    private final Button resetButton = new Button("Reset");

    private final HorizontalLayout buttonField = new HorizontalLayout();

    private List<Word> wordList;
    private String currentString; //current word.wordEN
    private int index;

    public WordFromLettersView(UserWordsService userWordsService) {
        this.userWordsService = userWordsService;

        configWordList();
        configTextFields();
        configNextButton();
        configCheckButton();
        configResetButton();

        add(task, answer, buttonField, new HorizontalLayout(nextButton, checkButton, resetButton));

        setAlignItems(Alignment.CENTER);
    }

    //TODO reset button
    private void configResetButton() {
        resetButton.setId("reset-button");
        resetButton.setEnabled(false);
        resetButton.addClickShortcut(Key.BACKSPACE);
        resetButton.addClickListener(buttonClickEvent -> {
            answer.setValue("");
            answer.setPrefixComponent(null);
            buttonField.removeAll();
            buttonField.add(configButtonField());
        });
    }

    private Collection<Component> configButtonField() {
        buttonField.setId("letters");
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
        wordList = userWordsService.getUnwritten();
        Collections.shuffle(wordList);
    }

    private void configNextButton() {
        nextButton.setId("next-button");
        nextButton.addClickShortcut(Key.SPACE);
        nextButton.addClickListener(buttonClickEvent -> {
            nextButton.setText("Next");
            nextButton.setEnabled(false);

            checkButton.setEnabled(true);
            resetButton.setEnabled(true);

            currentString = wordList.get(index).getWordEn();
            task.setValue(wordList.get(index).getWordRu());

            answer.setValue("");
            answer.setPrefixComponent(null);

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
            answer.setValue(answer.getValue() + button.getText());
            buttonClickEvent.getSource().setEnabled(false);
        });
        return button;
    }

    private void configCheckButton() {
        checkButton.setId("check-button");
        checkButton.addClickShortcut(Key.ENTER);
        checkButton.setEnabled(false);

        checkButton.addClickListener(buttonClickEvent -> {
            if (answer.getValue().equals(getCurrentString())) {
                answer.setPrefixComponent(VaadinIcon.CHECK.create());
                wordList.get(index - 1).setCompetently(true);
                userWordsService.update(wordList.get(index-1));
            } else {
                answer.setPrefixComponent(VaadinIcon.BAN.create());
                answer.setValue(answer.getValue() + "(" + currentString + ")");
            }
            buttonField.getChildren().forEach(component -> ((Button)component).setEnabled(false));
            nextButton.setEnabled(true);
        });
    }

    private void configTextFields() {
        task.setId("task-output");
        task.setReadOnly(true);
        task.getStyle().set("width", "21em");
        answer.setId("answer-input");
        answer.setReadOnly(true);
        answer.getStyle().set("width", "21em");
    }


}
