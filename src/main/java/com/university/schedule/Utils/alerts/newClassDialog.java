package com.university.schedule.Utils.alerts;

import com.university.schedule.model.Department;
import com.university.schedule.model.Schedule;
import com.university.schedule.model.SubjectTeacher;
import com.university.schedule.service.ScheduleService;
import com.university.schedule.service.SubjectTeacherService;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class newClassDialog implements Showable {

    @Override
    public Dialog show() {
        Dialog<Department> dialog = new Dialog<>();
        dialog.setTitle("Добавить новый факультет");
        dialog.setHeaderText("Введите информацию о новом факультете");
        dialog.setResizable(false);
        dialog.getDialogPane().setPrefSize(300, 500);
        Label name = new Label("Название: ");
        TextField nameTextField = new TextField();
        GridPane pane = new GridPane();
        pane.add(name, 0, 0);
        pane.add(nameTextField, 1, 0);
        dialog.getDialogPane().setContent(pane);
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        dialog.setResultConverter(buttonType -> {
            if(buttonType ==buttonTypeOk){
                Department department = new Department();
                department.setName(nameTextField.getText());
                return department;
            }
            return null;
        });
        return dialog;
    }
}
