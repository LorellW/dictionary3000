package com.github.lorellw.dictionary3000.views;

import com.github.lorellw.dictionary3000.services.ExerciseService;
import com.github.lorellw.dictionary3000.services.GrammarService;
import com.github.lorellw.dictionary3000.services.LessonService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Route(value = "lessons", layout = MainLayout.class)
public class LessonsView extends AbstractView{
    private ComboBox<Integer> lessonBox = new ComboBox<>();
    private ComboBox<Integer> exerciseBox = new ComboBox<>();
    private VerticalLayout contentLayout = new VerticalLayout();
    private List<OneTaskDiv> oneTaskDivList = new ArrayList<>();
    private Button checkButton = new Button("Check");

    private final GrammarService grammarService;
    private final ExerciseService exerciseService;
    private final LessonService lessonService;


    public LessonsView(GrammarService grammarService, ExerciseService exerciseService, LessonService lessonService) {
        this.grammarService = grammarService;
        this.exerciseService = exerciseService;
        this.lessonService = lessonService;

        configComboBoxes();
        configCheckButton();

        add(lessonBox,exerciseBox,contentLayout,checkButton);
    }

    private void configComboBoxes(){
        lessonBox.setItems(lessonService.getAllLessons());

        lessonBox.addValueChangeListener(event -> {
            exerciseBox.setItems(exerciseService.getAllExercise(lessonBox.getValue()));
        });
        exerciseBox.addValueChangeListener(event -> updateContent());

    }

    private void updateContent() {
        contentLayout.removeAll();
        oneTaskDivList.clear();

        grammarService.getTasksTextByLesson(lessonBox.getValue(), exerciseBox.getValue()).forEach(s -> {
            OneTaskDiv div = new OneTaskDiv(s);
            oneTaskDivList.add(div);
            contentLayout.add(div);
        });
    }

    private void configCheckButton(){
        checkButton.addClickListener(event -> {
            List<String> answers = new ArrayList<>();
            oneTaskDivList.forEach(div -> answers.add(div.getAnswerValue()));
            createResultDialog(grammarService.getWrongAnswers(answers,lessonBox.getValue(),exerciseBox.getValue())).open();
        });
    }

    private Dialog createResultDialog(Map<String,String> wrongAnswers) {
        Dialog resultDialog = new Dialog();
        resultDialog.setHeaderTitle("Your Result");
        wrongAnswers.forEach((s, s2) -> {
            resultDialog.add(new HorizontalLayout(new Text(s + " : "), new Text(s2)));
        });

        Button closeButton = new Button("Ok");
        closeButton.addClickListener(event -> {
            resultDialog.close();
        });

        resultDialog.add(closeButton);

        return resultDialog;
    }

    private static class OneTaskDiv extends Div {
        private final String taskText;
        private final TextField answer = new TextField();

        private OneTaskDiv(String taskText){
            this.taskText = taskText;
            add(new HorizontalLayout(new Text(taskText), answer));
        }

        public String getTaskText() {
            return taskText;
        }

        public String getAnswerValue() {
            return answer.getValue();
        }
    }
}
