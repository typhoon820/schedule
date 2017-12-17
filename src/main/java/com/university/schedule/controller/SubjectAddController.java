package com.university.schedule.controller;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.university.schedule.Utils.alerts.SuccessAlert;
import com.university.schedule.Utils.alerts.WarningAlert;
import com.university.schedule.model.Cycle;
import com.university.schedule.model.Subject;
import com.university.schedule.service.CycleService;
import com.university.schedule.service.SubjectService;
import com.university.schedule.service.SubjectServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class SubjectAddController extends AbstractController implements Initializable {


    @FXML
    private JFXTextField subjectTeaxtField;

    @FXML
    private JFXComboBox<Cycle> cycleComboBox;

    @FXML
    private JFXRadioButton creditRadioButton;

    @FXML
    private JFXRadioButton examRadioButton;

    @Autowired
    private CycleService cycleService;

    @Autowired
    private SubjectService subjectService;

    private ToggleGroup radioButtonGroup;


    @FXML
    void addNewSubject(ActionEvent event) {

        if (subjectTeaxtField.getText().trim().isEmpty()
                || cycleComboBox.getSelectionModel().getSelectedItem() == null
                || radioButtonGroup.getSelectedToggle() == null) {
            showAlert(new WarningAlert("Не все поля заполнены")).showAndWait();
        } else {
            if(!subjectTeaxtField.getText().trim().matches(".*[a-zа-я].*")){
                showAlert(new WarningAlert("Должна быть хотя бы одна буква")).showAndWait();
                return;
            }
            Subject subject = new Subject();
            subject.setName(subjectTeaxtField.getText());
            subject.setCycleByCycleId(cycleComboBox.getSelectionModel().getSelectedItem());
            subject.setExam(examRadioButton.isSelected());
            if (subjectService.findByName(subject.getName()) != null) {
                showAlert(new WarningAlert("Такой предмет уже есть")).showAndWait();
            } else {
                subjectService.save(subject);
                Optional<ButtonType> res = showAlert(new SuccessAlert("Предмет успешно добавлен")).showAndWait();
                if (res.get() == ButtonType.OK) {
                    ((Stage) subjectTeaxtField.getScene().getWindow()).close();
                }
            }
        }
    }

    @FXML
    void selectCredit(ActionEvent event) {

    }

    @FXML
    void selectExam(ActionEvent event) {

    }

    private void initComboBox() {
        ObservableList<Cycle> cycles = FXCollections.observableArrayList();
        cycles.setAll(cycleService.findAll());
        cycleComboBox.setItems(cycles);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radioButtonGroup = new ToggleGroup();
        creditRadioButton.setToggleGroup(radioButtonGroup);
        examRadioButton.setToggleGroup(radioButtonGroup);

        initComboBox();
    }
}
