package com.university.schedule.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.university.schedule.Utils.alerts.WarningAlert;
import com.university.schedule.model.*;
import com.university.schedule.observers.Observable;
import com.university.schedule.observers.Observer;
import com.university.schedule.service.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.events.Event;

import javax.swing.event.ListDataEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.util.stream.Collectors.toList;

@Component
public class EditTeacherController extends AbstractController implements Initializable,
        Observable {

    @FXML
    private JFXTextField fioTextField;

    @FXML
    private JFXTextField addressTextField;

    @FXML
    private JFXTextField phoneTextField;

    @FXML
    private JFXComboBox<String> degreeComboBox;

    @FXML
    private JFXComboBox<Department> departmentComboBox;

    @FXML
    private JFXComboBox<Position> positionComboBox;

    @FXML
    private TableView<Subject> subjectsTable;

    @FXML
    private TableColumn<Subject, String> subjectCol;

    @FXML
    private TableColumn<Subject, String> examCol;

    @FXML
    private TableColumn<Subject, Boolean> tickCol;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private PositionService positionService;


    @Autowired
    private SubjectTeacherService subjectTeacherService;

    private Position oldPosition;

    private void initCols(){
        subjectsTable.setEditable(true);
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        examCol.setCellValueFactory(new PropertyValueFactory<>("exam"));
        tickCol.setCellFactory(CheckBoxTableCell.forTableColumn(tickCol));
        tickCol.setCellValueFactory(new PropertyValueFactory<>("chosen"));
        ObservableList<Department> departments = FXCollections.observableArrayList();
        departments.setAll(departmentService.findAll());
        departmentComboBox.setItems(departments);
        ObservableList<String> degrees = FXCollections.observableArrayList("профессор", "доцент", "Магистр");
        positionComboBox.getItems().setAll(positionService.findAll());
        degreeComboBox.setItems(degrees);
    }

    private void loadData(){
        List<Subject> allSubjects = subjectService.findAll();
        List<SubjectTeacher> subjectTeachers = subjectTeacherService.findByTeacherID(teacher);
        List<Subject> subjects = subjectTeachers.stream().map(SubjectTeacher::getSubjectBySubjectId).collect(toList());
        allSubjects.forEach(s-> s.setChosen(new SimpleBooleanProperty(subjects.contains(s))));
        subjectsTable.getItems().setAll(allSubjects);
    }

    private void initFields(){
        oldPosition = teacher.getPositionByPositionId();
        fioTextField.setText(teacher.getFio());
        addressTextField.setText(teacher.getAdress());
        phoneTextField.setText(teacher.getPhone());
        degreeComboBox.setValue(teacher.getScientificDegree());

        departmentComboBox.setValue(teacher.getDepartmentByDepartmentId());
        positionComboBox.setValue(oldPosition);
    }

    @FXML
    void editTeacher(ActionEvent event) {

        if (fioTextField.getText().trim().isEmpty()
                || addressTextField.getText().trim().isEmpty()
                || phoneTextField.getText().trim().isEmpty()
                || positionComboBox.getValue() == null
                || departmentComboBox.getValue() == null
                || degreeComboBox.getValue() == null){
            showAlert(new WarningAlert("необходимо заполнить все поля")).showAndWait();
            return;
        }
        if(phoneTextField.getText().length() < 6 && phoneTextField.getText().length() > 9){
            showAlert(new WarningAlert("Неверный формат телефона")).showAndWait();
            return;
        }
        try{
            Integer.valueOf(phoneTextField.getText());
        }
        catch (NumberFormatException e){
            showAlert(new WarningAlert("Неверный формат номера телефона")).showAndWait();
            return;
        }

        teacher.setFio(fioTextField.getText());
        teacher.setAdress(addressTextField.getText());
        Teacher t = teacherService.findByName(fioTextField.getText());
        if (t != null && t.getPhone().equals(phoneTextField.getText()) && !teacher.equals(t)){
            Alert al = showAlert(new WarningAlert("Такой преподаватель уже есть, попробуйте снова."));
            if (al.showAndWait().get() == ButtonType.OK) {
                initFields();
            }
            return;
        }
        teacher.setPhone(phoneTextField.getText());
        teacher.setScientificDegree(degreeComboBox.getSelectionModel().getSelectedItem());
        teacher.setDepartmentByDepartmentId(departmentComboBox.getValue());
        teacher.setPositionByPositionId(positionComboBox.getValue());

        if (!oldPosition.equals(teacher.getPositionByPositionId())) {
            for (Teacher teach : departmentComboBox.getValue().getTeachersById()) {
                if (teach.getPositionByPositionId().getPosition().equals("Заведующий кафедрой")) {
                    if (teacher.getPositionByPositionId().equals(teach.getPositionByPositionId())) {
                        showAlert(new WarningAlert("На кафедре уже есть заведующий")).showAndWait();
                        positionComboBox.setValue(oldPosition);
                        return;
                    }
                }
            }
        }

        List<Subject> chosen = new ArrayList<>();
        subjectsTable.getItems()
                .stream()
                .filter(i-> i.chosenProperty().get())
                .forEach(chosen::add);
        if(!teacher.getScientificDegree().equals("профессор")){
            if(!chosen.stream().filter(Subject::isExam).collect(toList()).isEmpty()){
                showAlert(new WarningAlert("Данный преподаватель не имеет неободимой ученой степени. " +
                        "Среди выбранных предметов есть те, по которым должен проводиться экзамен.")).showAndWait();
                loadData();
                return;
            }
        }
        List<SubjectTeacher> old = new ArrayList<>();
        old.addAll(teacher.getSubjectTeachersById());

        List<SubjectTeacher> subjectTeachers = new ArrayList<>();
        chosen.forEach(s-> subjectTeachers.add(new SubjectTeacher(s, teacher)));

        for(SubjectTeacher subj: old){
            if(!subjectTeachers.contains(subj)){
                teacher.getSubjectTeachersById().remove(subj);
            }
        }


        for(SubjectTeacher st: subjectTeachers){
            if(!teacher.getSubjectTeachersById().contains(st)){
                teacher.getSubjectTeachersById().add(st);
            }
        }

        teacherService.update(teacher);
        ((Stage)(fioTextField.getScene().getWindow())).close();
        notifyObservers();
    }



    private Teacher teacher;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        teacher = teacherTransfer;
        initFields();
        initCols();
        loadData();

        registerObserver((Observer) ControllerTransfer.getInstance().getController());

    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o: observers){
            o.update();
        }
    }
}
