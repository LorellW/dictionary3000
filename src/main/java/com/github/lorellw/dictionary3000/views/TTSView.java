package com.github.lorellw.dictionary3000.views;

import com.github.lorellw.dictionary3000.entities.Word;
import com.github.lorellw.dictionary3000.services.WordService;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;
import java.util.List;


@PageTitle("Text to Speech")
@Route(value = "texttospeech", layout = MainLayout.class)
public class TTSView extends AbstractView {
    private TextField inputField = new TextField("Label");

    private Button nextButton = new Button("Start");
    private Button playButton = new Button("Play", VaadinIcon.PLAY.create());
    private Button checkButton = new Button("Check");
    private final WordService wordService;

    private List<Word> wordList;
    private String currentString; //current word.wordEN
    private int index;


    public TTSView(WordService wordService) {
        System.setProperty("freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        this.wordService = wordService;

        configWordList();
        configNextButton();
        configPlayButton();
        configCheckButton();

        add(inputField, new HorizontalLayout(checkButton, playButton, nextButton));
        setAlignItems(Alignment.CENTER);
    }

    private void configWordList() {
        //wordList = wordService.getUnwritten();
        Collections.shuffle(wordList);
    }

    private String getCurrentString() {
        if (currentString.contains("(")) {
            return currentString.substring(0, currentString.indexOf("(")).trim();
        } else {
            return currentString;
        }
    }

    private void configPlayButton() {
        playButton.setEnabled(false);
        playButton.addClickListener(buttonClickEvent -> {
            speak(getCurrentString());
        });
    }

    private void configNextButton() {
        nextButton.addClickShortcut(Key.SPACE);
        nextButton.addClickListener(buttonClickEvent -> {
            nextButton.setText("Next");
            nextButton.setEnabled(false);
            currentString = wordList.get(index).getWordEn();

            inputField.setValue("");
            inputField.setPrefixComponent(null);

            playButton.setEnabled(true);
            index++;
        });
    }

    private void configCheckButton() {
        checkButton.addClickShortcut(Key.ENTER);
        checkButton.addClickListener(buttonClickEvent -> {
            if (inputField.getValue().equals(currentString)){
                inputField.setPrefixComponent(VaadinIcon.CHECK.create());
                wordList.get(index - 1).setListened(true);
                wordService.saveWord(wordList.get(index-1));
            } else {
                inputField.setPrefixComponent(VaadinIcon.BAN.create());
                inputField.setValue(currentString);
            }
            nextButton.setEnabled(true);
        });
    }


    private void speak(String planeText) {
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        voice.allocate();
        voice.speak(planeText);
        voice.deallocate();
    }
}
