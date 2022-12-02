package com.github.lorellw.dictionary3000.views;

import com.github.lorellw.dictionary3000.entities.Languages;
import com.github.lorellw.dictionary3000.entities.Word;
import com.github.lorellw.dictionary3000.services.WordService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.*;

@PageTitle("Translate")
@Route(value = "translate", layout = MainLayout.class)
public class TranslateView extends VerticalLayout {
    private final TextField wordToTranslate = new TextField();

    private final Button answer1 = new Button("answer #1");
    private final Button answer2 = new Button("answer #2");
    private final Button answer3 = new Button("answer #3");
    private final Button answer4 = new Button("answer #4");

    private Languages langMode = Languages.enEN;

    private final Button[] buttons = {answer1, answer2, answer3, answer4};

    private int index = 0;

    private final WordService wordService;
    private List<Word> wordList;

    public TranslateView(WordService wordService) {
        this.wordService = wordService;
        configWordList();
        configTextField();

        add(createChangeLangTabs(),
                wordToTranslate,
                createButtonField(),
                createNextButton());
        setAlignItems(Alignment.CENTER);
    }

    private void configTextField() {
        wordToTranslate.setReadOnly(true);
        wordToTranslate.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        wordToTranslate.getStyle().set("width", "21em");
    }

    private void configWordList() {
        wordList = wordService.getUntranslated(langMode);
        Collections.shuffle(wordList);
    }

    private VerticalLayout createButtonField() {
        VerticalLayout buttonField = new VerticalLayout();

        answer1.addClickShortcut(Key.DIGIT_1);
        answer2.addClickShortcut(Key.DIGIT_2);
        answer3.addClickShortcut(Key.DIGIT_3);
        answer4.addClickShortcut(Key.DIGIT_4);

        Arrays.stream(buttons).forEach(button -> {
            button.setEnabled(false);
            button.addClickListener(buttonClickEvent -> configAnswerButton(button));
        });
        buttonField.add(buttons);
        buttonField.setAlignItems(Alignment.CENTER);
        return buttonField;
    }

    private Button createNextButton() {
        Button nextButton = new Button("Start");
        nextButton.addClickShortcut(Key.SPACE);
        nextButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        nextButton.addClickListener(buttonClickEvent -> {
            nextButton.setText("Next");

            if (langMode == Languages.enEN) {
                wordToTranslate.setValue(wordList.get(index).getWordEn());
            } else if (langMode == Languages.ruRU) {
                wordToTranslate.setValue(wordList.get(index).getWordRu());
            }
            List<String> answersPool = generateAnswersPool();

            for (int i = 0; i < 4; i++) {
                buttons[i].setText(answersPool.get(i));
                buttons[i].setIcon(null);
                buttons[i].setEnabled(true);
            }

            index++;
        });
        return nextButton;
    }

    private List<String> generateAnswersPool() {
        List<String> answersPool = new ArrayList<>();
        if (langMode == Languages.enEN) {
            answersPool.add(wordList.get(index).getWordRu());
            answersPool.add(wordList.get(new Random().nextInt(wordList.size())).getWordRu());
            answersPool.add(wordList.get(new Random().nextInt(wordList.size())).getWordRu());
            answersPool.add(wordList.get(new Random().nextInt(wordList.size())).getWordRu());
        } else if (langMode == Languages.ruRU) {
            answersPool.add(wordList.get(index).getWordEn());
            answersPool.add(wordList.get(new Random().nextInt(wordList.size())).getWordEn());
            answersPool.add(wordList.get(new Random().nextInt(wordList.size())).getWordEn());
            answersPool.add(wordList.get(new Random().nextInt(wordList.size())).getWordEn());
        }
        Collections.shuffle(answersPool);
        return answersPool;
    }

    private void configAnswerButton(Button button) { //TODO rewrite, all buttons should be disable after answer
        if (button.getText().equals(wordList.get(index - 1).getWordRu()) ||
                button.getText().equals(wordList.get(index - 1).getWordEn())) {
            button.setIcon(new Icon(VaadinIcon.CHECK));
            wordList.get(index - 1).setTranslated(langMode, true);
            wordService.update(wordList.get(index - 1));
        } else {
            button.setIcon(new Icon(VaadinIcon.BAN));
            Arrays.stream(buttons).forEach(b -> {
                if (b.getText().equals(wordList.get(index - 1).getWordRu()) ||
                        b.getText().equals(wordList.get(index - 1).getWordEn())) {
                    b.setIcon(new Icon(VaadinIcon.CHECK));
                }
                b.setEnabled(false);
            });
        }
    }

    private Tabs createChangeLangTabs() {
        Tabs languagesTabs = new Tabs();

        Tab enTab = new Tab("English to Russian");
        Tab ruTab = new Tab("Russian to English");


        languagesTabs.addSelectedChangeListener(selectedChangeEvent -> {
            if (selectedChangeEvent.getSelectedTab().equals(enTab)) {
                langMode = Languages.enEN;
                configWordList();
            } else if (selectedChangeEvent.getSelectedTab().equals(ruTab)) {
                langMode = Languages.ruRU;
                configWordList();
            }
        });

        languagesTabs.add(enTab, ruTab);
        return languagesTabs;
    }
}
