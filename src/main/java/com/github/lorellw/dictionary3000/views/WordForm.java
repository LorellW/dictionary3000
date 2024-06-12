package com.github.lorellw.dictionary3000.views;

import com.github.lorellw.dictionary3000.entities.Word;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class WordForm extends FormLayout {

    private Word word;

    protected TextField wordEn = new TextField("English");
    protected TextField wordRu = new TextField("Russian");

    protected Button saveButton = new Button("Save", VaadinIcon.PLUS.create());
    protected Button cancelButton = new Button("Cancel");

    private Binder<Word> binder = new BeanValidationBinder<>(Word.class);

    public WordForm() {
        wordEn.setId("input-word-en");
        wordRu.setId("input-word-ru");
        saveButton.setId("save-button");
        binder.bindInstanceFields(this);
        add(wordEn, wordRu, createButtonField());

    }

    private HorizontalLayout createButtonField() {
        saveButton.addClickShortcut(Key.ENTER);
        cancelButton.addClickShortcut(Key.ESCAPE);

        saveButton.addClickListener(buttonClickEvent -> validateAndSave());
        cancelButton.addClickListener(buttonClickEvent -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(statusChangeEvent -> saveButton.setEnabled(binder.isValid()));
        return new HorizontalLayout(saveButton, cancelButton);
    }

    public void setWord(Word word){
        this.word = word;
        binder.readBean(word);
    }

    private void validateAndSave(){
        try {
            binder.writeBean(word);
            fireEvent(new SaveEvent(this,word));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class WordFormEvent extends ComponentEvent<WordForm> {
        private Word word;

        protected WordFormEvent(WordForm source, Word word) {
            super(source, false);
            this.word = word;
        }

        public Word getWord() {
            return word;
        }
    }

    public static class SaveEvent extends WordFormEvent {
        SaveEvent(WordForm source, Word word) {
            super(source, word);
        }
    }

    public static class CloseEvent extends WordFormEvent {
        CloseEvent(WordForm source) {
            super(source,null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener){
        return getEventBus().addListener(eventType,listener);
    }
}
