package com.university.schedule.controller;

import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.university.schedule.Utils.CycleStats;
import com.university.schedule.Utils.alerts.ConfirmAlert;
import com.university.schedule.Utils.alerts.SuccessAlert;
import com.university.schedule.Utils.alerts.WarningAlert;
import com.university.schedule.model.*;
import com.university.schedule.service.GroupService;
import com.university.schedule.service.ScheduleService;
import com.university.schedule.service.SubjectTeacherService;
import com.university.schedule.service.TimetableService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClassAddController extends AbstractController implements Initializable {

    private ObservableList<Subject> subjects = FXCollections.observableArrayList();
    private ObservableList<Teacher> teachers = FXCollections.observableArrayList();
    private List<SubjectTeacher> availiableTeachers = new ArrayList<>();

    @FXML
    private JFXComboBox<Groups> groupsComboBox;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXComboBox<Timetable> timeComboBox;

    @FXML
    private JFXTextField roomTextField;

    @FXML
    private JFXComboBox<Subject> subjectComboBox;

    @FXML
    private JFXComboBox<Teacher> teacherComboBox;

    private Schedule newClass;

    @Autowired
    private SubjectTeacherService subjectTeacherService;

    @Autowired
    private TimetableService timetableService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    GroupService groupService;

    private Timetable selected;
    private Teacher selectedTeacher;
    private Groups selectedGroup;

    private String timestamp = "";
    private String time = "";
    private String timeVal = "";

    private void initGroups() {
        ObservableList<Groups> groups = FXCollections.observableArrayList();
        groups.addAll(groupService.findAll());
        groupsComboBox.setItems(groups);
    }

    private void initTimes() {
        ObservableList<Timetable> times = FXCollections.observableArrayList();
        times.setAll(timetableService.findAll());
        timeComboBox.setItems(times);
    }

    private void initSubjectTeachers() {
        subjects.clear();
        List<SubjectTeacher> allsubjects = subjectTeacherService.findAll();
        for (SubjectTeacher s : allsubjects) {
            if (!subjects.contains(s.getSubjectBySubjectId()))
                subjects.add(s.getSubjectBySubjectId());
        }
        subjectComboBox.setItems(subjects);
    }

    int savedId= 0;
    @FXML
    void addNewClass(ActionEvent event) {
        if (selectedGroup == null
                || selected == null
                || selectedTeacher == null
                || time.isEmpty()
                || timestamp.isEmpty()
                || roomTextField.getText().isEmpty()) {
            showAlert(new WarningAlert("Не все поля заполнены")).showAndWait();
        } else {
            Integer room = 0;
            try {
                room = Integer.parseInt(roomTextField.getText(), 10);
            } catch (NumberFormatException e) {
                showAlert(new WarningAlert("Неверный номер аудитории")).showAndWait();
                roomTextField.setText("");
                return;
            }
            timeVal = timestamp + " " + time + ".000000000";
            newClass.setRoom(room);
            newClass.setStartTime(Timestamp.valueOf(timeVal));
            newClass.setTimetableId(selected.getClassNumber());
            newClass.setSubjectTeacherBySubjectTeacherId(find(selectedTeacher));
            newClass.setGroupNumber(selectedGroup.getGroupNo());

            boolean saved = false;
            List<Schedule> busyTime = scheduleService.findByTime(selected.getClassNumber());
            List<Teacher> busyTeachers = busyTime.stream()
                    .filter(s-> s.getStartTime().equals(newClass.getStartTime()))
                    .map(s -> s.getSubjectTeacherBySubjectTeacherId().getTeacherByTeacherId())
                    .collect(Collectors.toList());
            if (busyTime.size() == 0) {
                scheduleService.save(newClass);
                //savedId = scheduleService.saveWithId(newClass);
                saved = true;

            } else {
                if (busyTeachers.contains(selectedTeacher)) {
                    showAlert(new WarningAlert("Преподаватель занят в данное время")).showAndWait();
                    teacherComboBox.setValue(null);

                } else {
                    for (Schedule s : busyTime) {
                        if (!s.getRoom().equals(room)) {
//                            scheduleService.save(newClass);
                            scheduleService.save(newClass);

                            saved = true;
                            break;
                        }

                    }
                    if (!saved) {
                        showAlert(new WarningAlert("Данная аудитоия в это время занята")).showAndWait();
                        roomTextField.setText("");
                        return;
                    }
                }
            }
            if (saved) {
                Optional<CycleStats> cs = getStats(selectedGroup).stream().filter(stats->stats.getPercent()>70).findFirst();
                if(cs.isPresent()){
                    Optional<ButtonType> res = showAlert(new ConfirmAlert("Если добавить занятие, процент цикла "+cs.get().getCycle().getName()+" будет больше 70%")).showAndWait();
                    if(res.get() == ButtonType.CANCEL){
                        scheduleService.delete(newClass.getId());
                        return;
                    }
                }
                Optional<ButtonType> res = showAlert(new SuccessAlert("Предмет успешно добавлен в расписание")).showAndWait();
                if (res.isPresent()) {
                    ((Stage) roomTextField.getScene().getWindow()).close();
                }
            }

        }
    }

    private SubjectTeacher find(Teacher t) {
        if (t == null) {
            showAlert(new WarningAlert("Укажите преподавателя"));
        } else {
            for (SubjectTeacher st : availiableTeachers) {
                if (st.getTeacherByTeacherId().equals(t)) {
                    return st;
                }
            }
        }
        return null;
    }

    private List<CycleStats> getStats(Groups group){
        List<CycleStats> cycleStats = new ArrayList<>();
        Map<Cycle, Integer> stats = scheduleService.countSubjects(group);
        int sum = 0;
        for (Map.Entry<Cycle, Integer> entry : stats.entrySet()) {
            sum += entry.getValue();
        }

        for (Map.Entry<Cycle, Integer> entry : stats.entrySet()) {
            cycleStats.add(new CycleStats(entry.getKey(), entry.getValue(), new Double(entry.getValue())/sum*100));
        }
        return cycleStats;
    }

    @FXML
    void selectDate(ActionEvent event) {
        LocalDate date = datePicker.getValue();
        timestamp = date.toString();
    }

    @FXML
    void selectSubject(ActionEvent event) {
        teachers.clear();
        teacherComboBox.setValue(null);
        availiableTeachers = subjectTeacherService.findBySubjectID(subjectComboBox.getSelectionModel().getSelectedItem());
        for (SubjectTeacher t : availiableTeachers) {
            teachers.add(t.getTeacherByTeacherId());
        }
        teacherComboBox.setItems(teachers);
    }

    @FXML
    void selectTeacher(ActionEvent event) {
        selectedTeacher = teacherComboBox.getSelectionModel().getSelectedItem();
    }

    @FXML
    void selectTime(ActionEvent event) {
        selected = timeComboBox.getSelectionModel().getSelectedItem();
        time = selected.getStartTime().toString();
    }

    @FXML
    void selectGroup(ActionEvent event) {
        selectedGroup = groupsComboBox.getSelectionModel().getSelectedItem();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newClass = new Schedule();
        initTimes();
        initGroups();
        initSubjectTeachers();
    }
}
