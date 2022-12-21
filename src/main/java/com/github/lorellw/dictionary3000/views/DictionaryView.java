package com.github.lorellw.dictionary3000.views;

import com.github.lorellw.dictionary3000.enums.Languages;
import com.github.lorellw.dictionary3000.entities.Word;
import com.github.lorellw.dictionary3000.enums.Status;
import com.github.lorellw.dictionary3000.services.WordService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Dictionary 3000")
@Route(value = "3000", layout = MainLayout.class)
public class DictionaryView extends AbstractView {
    private final Grid<Word> grid = new Grid<>(Word.class, false);
    private final WordService wordService;

    private final TextField filter = new TextField();
    private final Button addButton = new Button("Add");
    private final Select<Status> statusSelect = new Select<>();
    private Status status = null;

    protected WordForm form;

    public DictionaryView(WordService wordService) {
        this.wordService = wordService;

        configGrid();
        configForm();
        configuredFilter();
        configAddButton();
        configSelect();

        add(new HorizontalLayout(filter, statusSelect), addButton, getContent());
        addClassName("dictionary-view");
        setSizeFull();
        updateList();
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configGrid() {
        createContentGrid();

        grid.addClassName("words-grid");
        grid.setSizeFull();
        grid.addColumn(Word::getWordEn).setHeader("Eng");
        grid.addColumn(Word::getWordRu).setHeader("Rus");
        grid.addComponentColumn(word -> {
            if (word.getStatus() == Status.NEW) {
                return new Icon(VaadinIcon.BOOK);
            }
            if (word.getStatus() == Status.ONSTUDY) {
                return new Icon(VaadinIcon.CLOCK);
            }
            if (word.getStatus() == Status.STUDIED) {
                return new Icon(VaadinIcon.CHECK);
            }
            return new Icon(VaadinIcon.TOOLS);
        }).setHeader("Status");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        add(grid);
    }

    private void configSelect() {
        statusSelect.setPlaceholder("Status");
        statusSelect.setEmptySelectionAllowed(true);
        statusSelect.setItemLabelGenerator(s -> {
            if (s == null){
                return "";
            }
            return s.getName();
        });
        statusSelect.setItems(Status.NEW,Status.ONSTUDY,Status.STUDIED);
        statusSelect.addValueChangeListener(listener -> {
            status = listener.getValue();
            updateList();
        });
    }

    private void configAddButton() {
        addButton.addClickListener(buttonClickEvent -> {
            if (form.isVisible()) {
                form.setVisible(false);
            } else {
                grid.asSingleSelect().clear();
                editContact(new Word());
            }
        });
    }

    private void editContact(Word word) {
        if (word == null) {
            closeEditor();
        } else {
            form.setWord(word);
            form.setVisible(true);
        }
    }

    private void createContentGrid() {
        GridContextMenu<Word> menu = grid.addContextMenu();
        menu.addItem("Learned", event -> {
            if (event.getItem().isPresent()) {
                Word learnedWord = event.getItem().get();
                learnedWord.setTranslated(Languages.ALL, true);
                learnedWord.setCompetently(true);
                wordService.update(learnedWord);
                updateList();
            }
        });
        menu.addItem(new Hr());
        menu.addItem("Edit", event -> {
            if (event.getItem().isPresent()) {
                editContact(event.getItem().get());
            }
        });
    }

    private void configForm() {
        form = new WordForm();
        form.setWidth("25em");
        form.addListener(WordForm.CloseEvent.class, closeEvent -> closeEditor());
        form.addListener(WordForm.SaveEvent.class, this::saveContact);
    }

    private void saveContact(WordForm.SaveEvent event) {
        wordService.saveWord(event.getWord());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setVisible(false);
    }

    private void updateList() {
        grid.setItems(wordService.getAll(filter.getValue(),status));
    }

    private void configuredFilter() {
        filter.setPlaceholder("Search");
        filter.setPrefixComponent(VaadinIcon.SEARCH.create());
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(textFieldStringComponentValueChangeEvent -> updateList());
    }
}
