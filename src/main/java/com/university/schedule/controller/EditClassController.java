package com.university.schedule.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.university.schedule.Utils.CycleStats;
import com.university.schedule.Utils.alerts.ConfirmAlert;
import com.university.schedule.Utils.alerts.SuccessAlert;
import com.university.schedule.Utils.alerts.WarningAlert;
import com.university.schedule.model.*;
import com.university.schedule.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class EditClassController extends AbstractController implements Initializable{


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

    private Schedule old;

    @Autowired
    TeacherService teacherService;

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
        groupsComboBox.getSelectionModel().select(groupService.findAll().stream().filter(g->g.getGroupNo().equals(scheduleTransfer.getGroupNumber())).findFirst().get());
    }

    private void initTimes() {
        ObservableList<Timetable> times = FXCollections.observableArrayList();
        times.setAll(timetableService.findAll());
        timeComboBox.setItems(times);
        timeComboBox.getSelectionModel().select(timetableService.findAll().stream().filter(t->t.getClassNumber() == scheduleTransfer.getTimetableId()).findFirst().get());
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
    void editClass(ActionEvent event) {

        roomTextField.requestFocus();
        if (selectedGroup == null
                || selected == null
                || time.isEmpty()
                || timestamp.isEmpty()
                || roomTextField.getText().isEmpty()) {
            showAlert(new WarningAlert("Не все поля заполнены")).showAndWait();
        } else {
            Integer room = 0;
            LocalDate localDate = LocalDate.of(1900,1,1);
            if(datePicker.getValue().isBefore(LocalDate.of(1900,1,1))){
                showAlert(new WarningAlert("Доисторические времена, всех студенты уже стали профессорами")).showAndWait();
                return;
            }
            if (datePicker.getValue().isBefore(LocalDate.now().minusMonths(3))){
                showAlert(new WarningAlert("Недопустимая дата")).showAndWait();
                return;
            }
            if (datePicker.getValue().isAfter(LocalDate.now().plusMonths(12))){
                showAlert(new WarningAlert("Дальше чем на год планировать расписание так себе затея")).showAndWait();
                return;
            }
            try {
                room = Integer.parseInt(roomTextField.getText(), 10);
            } catch (NumberFormatException e) {
                showAlert(new WarningAlert("Неверный номер аудитории")).showAndWait();
                roomTextField.setText("");
                return;
            }
            timeVal = timestamp + " " + time + ".000000000";
            scheduleTransfer.setRoom(room);
            scheduleTransfer.setStartTime(Timestamp.valueOf(timeVal));
            scheduleTransfer.setTimetableId(selected.getClassNumber());
            scheduleTransfer.setSubjectTeacherBySubjectTeacherId(find(selectedTeacher));
            scheduleTransfer.setGroupNumber(selectedGroup.getGroupNo());

            if(scheduleTransfer.equals(old)){
                return;
            }

            boolean saved = false;
            List<Schedule> busyTime = scheduleService.findByTime(selected.getClassNumber());
            List<Teacher> busyTeachers = busyTime.stream()
                    .filter(s-> s.getStartTime().equals(scheduleTransfer.getStartTime()))
                    .map(s -> s.getSubjectTeacherBySubjectTeacherId().getTeacherByTeacherId())
                    .collect(Collectors.toList());
            if (busyTime.size() == 0) {
                scheduleService.update(scheduleTransfer);
                //savedId = scheduleService.saveWithId(scheduleTransfer);
                saved = true;

            } else {
                if (busyTeachers.contains(selectedTeacher)) {
                    showAlert(new WarningAlert("Преподаватель занят в данное время")).showAndWait();
                    teacherComboBox.setValue(null);

                } else {
                    for (Schedule s : busyTime) {
                        if (!s.getRoom().equals(room)) {
//                            scheduleService.save(scheduleTransfer);
                            scheduleService.update(scheduleTransfer);

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
                    Optional<ButtonType> res = showAlert(new ConfirmAlert("Если обновить занятие, процент цикла "+cs.get().getCycle().getName()+" будет больше 70%")).showAndWait();
                    if(res.get() == ButtonType.CANCEL){
                        scheduleService.delete(scheduleTransfer.getId());
                        old.setId(0);
                        scheduleService.save(old);
                        return;
                    }
                }
                Optional<ButtonType> res = showAlert(new SuccessAlert("Предмет успешно обновлен")).showAndWait();
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
        old = scheduleTransfer.copy();
        roomTextField.setText(scheduleTransfer.getRoom().toString());
        teacherComboBox.setLabelFloat(false);
        subjectComboBox.setLabelFloat(false);
        timeComboBox.setLabelFloat(false);
        datePicker.setValue(LocalDate.parse(scheduleTransfer.getStartTime().toString().split(" ")[0]));
        initTimes();
        initGroups();
        initSubjectTeachers();
        teacherComboBox.setItems(FXCollections.observableArrayList(teacherService.findAll()));
        selectDate(null);
        selectGroup(null);
        subjectComboBox.setValue(scheduleTransfer.getSubjectTeacherBySubjectTeacherId().getSubjectBySubjectId());
        selectSubject(null);
        teacherComboBox.setValue(scheduleTransfer.getSubjectTeacherBySubjectTeacherId().getTeacherByTeacherId());
        selectTeacher(null);
        selectDate(null); //TODO: fix add teacher
        selectTime(null);
//        subjectComboBox.getSelectionModel().select(scheduleTransfer.getSubjectTeacherBySubjectTeacherId().getSubjectBySubjectId());
//        teacherComboBox.getSelectionModel().select(scheduleTransfer.getSubjectTeacherBySubjectTeacherId().getTeacherByTeacherId());
    }
}
