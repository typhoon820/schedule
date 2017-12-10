package com.university.schedule.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.university.schedule.Utils.TeacherNameTransfer;
import com.university.schedule.Utils.Utils;
import com.university.schedule.model.Schedule;
import com.university.schedule.model.Teacher;
import com.university.schedule.service.ScheduleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class TeacherScheduleController extends AbstractController implements Initializable {



    private ObservableList<Schedule> list = FXCollections.observableArrayList();
    private ObservableList<Schedule> scheduleByDate = FXCollections.observableArrayList();

    @FXML
    private JFXDatePicker calendar;

    @FXML
    private Label teacherNameLabel;

    @FXML
    private TableView<Schedule> scheduleTable;

    @FXML
    private TableColumn<Schedule, String> subjectCol;

    @FXML
    private TableColumn<Schedule, String> groupCol;

    @FXML
    private TableColumn<Schedule, String> roomCol;

    @FXML
    private TableColumn<Schedule, String> startTimeCol;

    @Autowired
    ScheduleService scheduleService;

    @FXML
    void updateTable(ActionEvent event) {
        LocalDate selectedDate = calendar.getValue();
        System.out.println(selectedDate);
        Teacher currentTeacher = TeacherNameTransfer.getInstance().getTchr();
        System.out.println(list.get(0).getStartTime().toString());

        for (Schedule s: list){
            if (Utils.cutTime(s.getStartTime().toString()).equals(selectedDate.toString())){
                scheduleByDate.add(s);
            }
        }
        scheduleTable.getItems().clear();
        scheduleTable.getItems().setAll(scheduleByDate);

        //TODO: cut sting to date and compare to get schedule for a certain date
    }

    private void initCols(){
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subj"));
        groupCol.setCellValueFactory(new PropertyValueFactory<>("groupNumber"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));

//        calendar.setConverter(new StringConverter<LocalDate>() {
//            String format = "dd.mm.yyyy";
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
//            @Override
//            public String toString(LocalDate localDate) {
//                if (localDate != null){
//                    return dateTimeFormatter.format(localDate);
//                }
//                else {
//                    return "";
//                }
//            }
//
//            @Override
//            public LocalDate fromString(String s) {
//                if(s != null && !s.isEmpty()){
//                    return LocalDate.parse(s,dateTimeFormatter);
//                }
//                else {
//                    return null;
//                }
//            }
//        });
    }

    private void loadData(){
        scheduleTable.getItems().clear();
        list.clear();
        List<Schedule> teacherSchedule = scheduleService.findByTeacher(TeacherNameTransfer.getInstance().getTchr());
        list.addAll(teacherSchedule);
        scheduleTable.getItems().setAll(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TeacherNameTransfer t = TeacherNameTransfer.getInstance();
        //System.out.println(TeacherNameTransfer.getInstance().getName());
        teacherNameLabel.setText(TeacherNameTransfer.getInstance().getTchr().getFio());
        initCols();
        loadData();
    }

    @FXML
    void loadAll(ActionEvent event) {
        scheduleTable.getItems().clear();
        loadData();
    }
}
