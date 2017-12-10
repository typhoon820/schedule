package com.university.schedule.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.university.schedule.Utils.CustomComboboxCell;
import com.university.schedule.Utils.alerts.WarningAlert;
import com.university.schedule.model.Department;
import com.university.schedule.model.Position;
import com.university.schedule.model.Teacher;
import com.university.schedule.observers.Observable;
import com.university.schedule.observers.Observer;
import com.university.schedule.service.DepartmentService;
import com.university.schedule.service.PositionService;
import com.university.schedule.service.TeacherService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

@Component
public class TeacherAddController extends AbstractController implements Initializable, Observable{


    private static final ObservableList<String> DGR = FXCollections.observableArrayList("профессор", "доцент", "Магистр");

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

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PositionService positionService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        degreeComboBox.getItems().setAll(DGR);
        departmentComboBox.getItems().setAll(departmentService.findAll());
        positionComboBox.getItems().setAll(positionService.findAll());
        departmentComboBox.setCellFactory(cell -> new CustomComboboxCell<>());
        positionComboBox.setCellFactory(cell-> new CustomComboboxCell<>());
    }

    private void clearFields(){
        fioTextField.setText("");
        addressTextField.setText(null);
        phoneTextField.setText("");
        degreeComboBox.setValue(null);
        departmentComboBox.setValue(null);
        positionComboBox.setValue(null);
    }

    @FXML
    void addNewTeacher(ActionEvent event) {

        Teacher teacher = new Teacher();

        teacher.setFio(fioTextField.getText());
        teacher.setAdress(addressTextField.getText());
        Teacher t = teacherService.findByName(fioTextField.getText());
        if (t != null && t.getPhone().equals(phoneTextField.getText())){
            Alert al = showAlert(new WarningAlert("Такой преподаватель уже есть, попробуйте снова."));
            if (al.showAndWait().get() == ButtonType.OK) {
                clearFields();
            }
            return;
        }
        teacher.setPhone(phoneTextField.getText());
        teacher.setScientificDegree(degreeComboBox.getSelectionModel().getSelectedItem());
        teacher.setDepartmentByDepartmentId(departmentComboBox.getValue());
        teacher.setPositionByPositionId(positionComboBox.getValue());

        for(Teacher teach: departmentComboBox.getValue().getTeachersById()){
            if(teach.getPositionByPositionId().getPosition().equals("Заведующий кафедрой")){
                if (teacher.getPositionByPositionId().equals(teach.getPositionByPositionId())){
                    showAlert(new WarningAlert("На кафедре уже есть заведующий")).showAndWait();
                    positionComboBox.setValue(null);
                    return;
                }
            }
        }

        teacherService.save(teacher);
        ((Stage)(fioTextField.getScene().getWindow())).close();
        notifyObservers();
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
        for (Observer o: observers){
            o.update();
        }
    }
}
