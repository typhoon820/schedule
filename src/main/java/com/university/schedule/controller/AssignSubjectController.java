package com.university.schedule.controller;


import com.jfoenix.controls.JFXComboBox;
import com.university.schedule.Utils.alerts.SuccessAlert;
import com.university.schedule.Utils.alerts.WarningAlert;
import com.university.schedule.model.Subject;
import com.university.schedule.model.SubjectTeacher;
import com.university.schedule.model.Teacher;
import com.university.schedule.service.SubjectService;
import com.university.schedule.service.SubjectTeacherService;
import com.university.schedule.service.TeacherService;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.management.OperatingSystemMXBean;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class AssignSubjectController extends AbstractController implements Initializable{

    @FXML
    private JFXComboBox<Teacher> teachersComboBox;

    @FXML
    private TableView<Subject> subjectsTable;

    @FXML
    private TableColumn<Subject, String> subjectCol;

    @FXML
    private TableColumn<Subject, String> examCol;

    @FXML
    private TableColumn<Subject, Boolean> tickCol;

    private Teacher selectedTeacher;

    @Autowired
    TeacherService teacherService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    SubjectTeacherService subjectTeacherService;

    private void initCols(){
        subjectsTable.setEditable(true);
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        examCol.setCellValueFactory(new PropertyValueFactory<>("exam"));
        tickCol.setEditable(true);
        tickCol.setCellFactory(CheckBoxTableCell.forTableColumn(tickCol));
        tickCol.setCellValueFactory(new PropertyValueFactory<>("chosen"));

//        tickCol.setOnEditCommit((TableColumn.CellEditEvent<Subject, Boolean> t) ->
//                (t.getTableView().getItems()
//                        .get(t.getTablePosition().getRow()))
//        .setChosen(!t.getOldValue()));

    }

    private void initComboBox(){
        ObservableList<Teacher> teachers = FXCollections.observableArrayList();
        teachers.addAll(teacherService.findAll());
        teachersComboBox.setItems(teachers);
    }

    private void loadData(){
        selectedTeacher = teachersComboBox.getSelectionModel().getSelectedItem();
        List<SubjectTeacher> teachersSubjects = subjectTeacherService.findByTeacherID(selectedTeacher);
        List<Subject> subjects = teachersSubjects
                .stream()
                .map(SubjectTeacher::getSubjectBySubjectId).collect(toList());

        List<Subject> availableSubjects = subjectService.findAll();
        availableSubjects.removeAll(subjects);
        subjectsTable.getItems().setAll(availableSubjects);

    }

    @FXML
    void assignSubject(ActionEvent event) {
        List<Subject> chosen = new ArrayList<>();
        //chosen.addAll(subjectsTable.getItems());
        subjectsTable.getItems()
                .stream()
                .filter(i-> i.chosenProperty().get())
                .forEach(chosen::add);
        if(!selectedTeacher.getScientificDegree().equals("Профессор")){
            if(!chosen.stream().filter(s-> s.isExam()).collect(toList()).isEmpty()){
                showAlert(new WarningAlert("Данный преподаватель не имеет неободимой ученой степени. " +
                        "Среди выбранных предметов есть те, по которым должен проводиться экзамен.")).showAndWait();
                loadData();
                return;
            }
        }
        chosen.forEach(s-> subjectTeacherService.save(new SubjectTeacher(s, selectedTeacher)));
        Optional<ButtonType> res = showAlert(new SuccessAlert("Предметы успешно распределены")).showAndWait();
        if(res.get() == ButtonType.OK){
            ((Stage)teachersComboBox.getScene().getWindow()).close();
        }
    }

    @FXML
    void selectTeacher(ActionEvent event) {
        loadData();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initComboBox();
        initCols();
    }
}
