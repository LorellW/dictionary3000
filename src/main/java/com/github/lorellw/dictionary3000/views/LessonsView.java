package com.github.lorellw.dictionary3000.views;

import com.github.lorellw.dictionary3000.dto.ExerciseDto;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@PageTitle("Grammatical lessons")
@Route(value = "lessons", layout = MainLayout.class)
public class LessonsView extends AbstractView{
    private final ComboBox<Integer> lessonBox = new ComboBox<>();
    private final ComboBox<Integer> exerciseBox = new ComboBox<>();
    private final VerticalLayout contentLayout = new VerticalLayout();
    private final List<OneTaskDiv> oneTaskDivList = new ArrayList<>();
    private final Button checkButton = new Button("Check");

    private final GrammarService grammarService;
    private final ExerciseService exerciseService;
    private final LessonService lessonService;

    // TODO may be Map<Integer, String>
    private Set<ExerciseDto> actualExercises;


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

        lessonBox.addValueChangeListener(event -> exerciseBox.setItems(exerciseService.getAllExerciseNumbers(lessonBox.getValue())));
        exerciseBox.addValueChangeListener(event -> {
            actualExercises = exerciseService.getAllExercise(lessonBox.getValue());
            updateContent();
        });

    }

    private void updateContent() {
        contentLayout.removeAll();
        oneTaskDivList.clear();
        actualExercises.forEach(exerciseDto -> {
            if (exerciseDto.getSeqNumb() == exerciseBox.getValue()){
                contentLayout.add(new Text(exerciseDto.getText()));
            }
        });
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

    private Dialog createResultDialog(List<String> wrongAnswers) {
        var resultDialog = new Dialog();
        resultDialog.setHeaderTitle("Your Result");
        wrongAnswers.forEach((s) -> resultDialog.add(new HorizontalLayout(new Text(s))));

        Button closeButton = new Button("Ok");
        closeButton.addClickListener(event -> resultDialog.close());

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
