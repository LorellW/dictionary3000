package com.github.lorellw.dictionary3000.views;

import com.github.lorellw.dictionary3000.entities.Word;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Dictionary 3000")
@Route(value = "3000", layout = MainLayout.class)
public class DictionaryView extends VerticalLayout {
    private final Grid<Word> grid = new Grid<>(Word.class, false);
    private final WordService wordService;
    private TextField filter = new TextField();
    protected WordForm form;

    public DictionaryView(WordService wordService) {
        this.wordService = wordService;
        Button addButton = new Button("Add");
        addButton.addClickListener(buttonClickEvent -> addWord());

        configGrid();
        configForm();
        configuredFilter();
        add(filter,addButton,getContent());
        addClassName("dictionary-view");
        setSizeFull();

        updateList();
        closeEditor();

    }


    private Component getContent(){
        HorizontalLayout content = new HorizontalLayout(grid,form);
        content.setFlexGrow(2,grid);
        content.setFlexGrow(1,form);
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
            int progress = word.getProgress();
            if (progress == 0) {
                return new Icon(VaadinIcon.BOOK);
            }
            if (progress == 1) {
                return new Icon(VaadinIcon.CLOCK);
            }
            if (progress == 2) {
                return new Icon(VaadinIcon.CHECK);
            }
            return new Icon(VaadinIcon.TOOLS);
        }).setHeader("Status");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        add(grid);
    }

    public void editContact(Word word) {
        if (word == null) {
            closeEditor();
        } else {
            form.setWord(word);
            form.setVisible(true);
        }
    }

    private void addWord(){
        grid.asSingleSelect().clear();
        editContact(new Word());
    }

    private void createContentGrid() {
        GridContextMenu<Word> menu = grid.addContextMenu();
        menu.addItem("Learned", event -> {
            if (event.getItem().isPresent()) {
                Word learnedWord = event.getItem().get();
                learnedWord.setProgress(2);
                wordService.update(learnedWord);
                updateList();
            }
        });
        menu.addItem(new Hr());

    }

    private void configForm(){
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

    private void closeEditor(){
        form.setVisible(false);
    }


    private void updateList() {
        grid.setItems(wordService.getAll(filter.getValue()));
    }

    private void configuredFilter() {
        filter.setPlaceholder("Filter...");
        filter.setPrefixComponent(VaadinIcon.SEARCH.create());
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(textFieldStringComponentValueChangeEvent -> updateList());
    }

//    private void add3000() throws IOException {
//        File file = new File("src/main/resources/dictionary.txt");
//        BufferedReader reader = new BufferedReader(new FileReader(file));
//        String temp = reader.readLine();
//        while (temp != null){
//            String[] newWord = temp.split("_");
//            Word word = new Word();
//            word.setWordEn(newWord[0]);
//            word.setWordRu(newWord[1]);
//            word.setProgress(0);
//            wordService.addWord(word);
//            System.out.println(word);
//            temp = reader.readLine();
//        }
//    }
}
