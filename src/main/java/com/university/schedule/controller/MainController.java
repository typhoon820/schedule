package com.university.schedule.controller;

import com.jfoenix.controls.JFXButton;
import com.university.schedule.DAO.CycleDAO;
import com.university.schedule.DAO.ScheduleDAO;
import com.university.schedule.Utils.CustomTableCell;
import com.university.schedule.Utils.CycleStats;
import com.university.schedule.Utils.TeacherNameTransfer;
import com.university.schedule.Utils.alerts.ConfirmAlert;
import com.university.schedule.Utils.alerts.SuccessAlert;
import com.university.schedule.Utils.alerts.WarningAlert;
import com.university.schedule.Utils.alerts.newClassDialog;
import com.university.schedule.model.*;
import com.university.schedule.observers.Observer;
import com.university.schedule.service.DepartmentService;
import com.university.schedule.service.GroupService;
import com.university.schedule.service.ScheduleService;
import com.university.schedule.service.TeacherService;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MainController extends AbstractController implements Initializable, Observer {


    ObservableList<Schedule> scheduleList = FXCollections.observableArrayList();
    ObservableList<Teacher> teacherList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Schedule, String> subjectCol;

    @FXML
    private TableColumn<Schedule, String> teacherCol;

    @FXML
    private TableColumn<Schedule, String> groupCol;

    @FXML
    private TableColumn<Schedule, String> roomCol;

    @FXML
    private TableColumn<Schedule, String> startTimeCol;
    @FXML
    private Tab scheduleTab;

    @FXML
    private TableView<Schedule> scheduleTable;

    @FXML
    private Tab teachersTab;

    @FXML
    private TableView<Teacher> teachersTable;

    @FXML
    private JFXButton addTeacherBtn;

    @FXML
    private JFXButton addSubjectBtn;

    @FXML
    private JFXButton addClassBtn;

    @FXML
    private JFXButton addDptBtn;

    @FXML
    private TableColumn<Teacher, String> teacherNameCol;

    @FXML
    private TableColumn<Teacher, String> addressCol;

    @FXML
    private TableColumn<Teacher, String> degreeCol;

    @FXML
    private TableColumn<Teacher, String> phoneCol;

    @FXML
    private TableColumn<Teacher, String> positionCol;

    @FXML
    private TableColumn<Teacher, String> deptCol;

    @FXML
    private JFXButton teacherScheduleButton;

    @FXML
    private JFXButton deleteTeacherButton;

    @FXML
    private JFXButton editTeacherButton;

    @FXML
    private ComboBox<Groups> groupComboBox;

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private ComboBox<String> yearComboBox;

    @FXML
    private JFXButton findFreeTeachers;
    @FXML
    private JFXButton stats;
    @FXML
    private JFXButton findButton;

    @FXML
    private ComboBox<String> scheduleMonthComboBox;

    @FXML
    private ComboBox<String> scheduleYearComboBox;

    @FXML
    private JFXButton editScheduleButton;

    @FXML
    private JFXButton deleteScheduleButton;


    @Autowired
    ScheduleService scheduleService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    GroupService groupService;


    private TeacherAddController teacherAddController;
    private EditTeacherController teacherEditController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        teacherAddController = (TeacherAddController) stageManager.getLoader().getController("../views/teacherAddForm.fxml");
        teacherAddController.registerObserver(this);
//        teacherEditController = (EditTeacherController)stageManager.getLoader().getController("../views/editTeacher.fxml");
//        teacherEditController.registerObserver(this);
        initCol();
        loadData();
        initTeacherCols();
        initMonthYears();
        initGroups();
        List<Schedule> s = scheduleService.findAll();
        System.out.println(s.get(0).getSubjectTeacherBySubjectTeacherId().getTeacherByTeacherId().getFio());
        editTeacherButton.setDisable(true);
        deleteTeacherButton.setDisable(true);
        teacherScheduleButton.setDisable(true);
        ControllerTransfer.getInstance().setController(this);

    }


    private void initMonthYears() {
        ObservableList<String> months = FXCollections.observableArrayList();
        ObservableList<String> years = FXCollections.observableArrayList();
        for (int i = 0; i < 12; i++) {
            if (i < 9)
                months.add('0' + String.valueOf(i + 1));
            else
                months.add(String.valueOf(i + 1));
            years.add(String.valueOf(2006 + i));
        }
        monthComboBox.setItems(months);
        yearComboBox.setItems(years);
        scheduleMonthComboBox.setItems(months);
        scheduleYearComboBox.setItems(years);

    }

    private void initGroups() {
        Groups empty = new Groups();
        empty.setGroupNo("Все");
        ObservableList<Groups> groups = FXCollections.observableArrayList();
        groups.setAll(groupService.findAll());
        groups.add(empty);
        groupComboBox.setItems(groups);
    }

    private void loadData() {
        scheduleTable.getItems().clear();
        scheduleList.clear();
        List<Schedule> schedules = scheduleService.findAll();
        scheduleList.addAll(schedules);
        scheduleTable.getItems().setAll(scheduleList);
    }

    private void loadTeacherData() {
        teachersTable.getItems().clear();
        teacherList.clear();
        List<Teacher> teachers = teacherService.findAll();
        for (Teacher t : teachers) {
            teacherList.add(t);
        }
        teachersTable.getItems().setAll(teacherList);
    }

    private void initCol() {
        teacherCol.setCellValueFactory(new PropertyValueFactory<>("tchr"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subj"));
        groupCol.setCellValueFactory(new PropertyValueFactory<>("groupNumber"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        teacherCol.setCellFactory(param -> new CustomTableCell<>());
    }

    private void initTeacherCols() {
        teacherNameCol.setCellValueFactory(new PropertyValueFactory<>("fio"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("adress"));
        degreeCol.setCellValueFactory(new PropertyValueFactory<>("scientificDegree"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("positionByPositionId"));
        deptCol.setCellValueFactory(new PropertyValueFactory<>("departmentByDepartmentId"));
        teacherNameCol.setCellFactory(teacherStringTableColumn -> new CustomTableCell<>());
        positionCol.setCellFactory(column -> new CustomTableCell<>());
        addressCol.setCellFactory(column -> new CustomTableCell<>());
        deptCol.setCellFactory(column -> new CustomTableCell<>());
    }


    @FXML
    void addClass(ActionEvent event) {
        stageManager.showStage("../views/classAddForm.fxml", false, "Добавить предмет в расписание");
    }

    @Autowired
    DepartmentService departmentService;

    @FXML
    void addDepartment(ActionEvent event) {
        Optional<Department> res = showDialog(new newClassDialog()).showAndWait();
        if (res.isPresent()) {
            if (res.get().getName().trim().isEmpty() || res.get().getName().length() < 3) {
                showAlert(new WarningAlert("Неверный формат ввода")).showAndWait();
                addDepartment(event);
                return;
            }
            if(!res.get().getName().trim().matches(".*[a-zа-я].*")){
                showAlert(new WarningAlert("Должна быть хотя бы одна буква")).showAndWait();
                addDepartment(event);
                return;

            }
            if (departmentService.findByName(res.get().getName()) == null) {
                departmentService.save(res.get());
                showAlert(new SuccessAlert("Факультет успешно добавлен")).showAndWait();
            } else {
                showAlert(new WarningAlert("Такой факультет уже существует")).showAndWait();
            }
        }
    }


    @FXML
    void addSubject(ActionEvent event) {

        stageManager.showStage("../views/subjectAddForm.fxml", false, "Новый предмет");
    }

    @FXML
    void addTeacher(ActionEvent event) {
        //((Stage)(addDptBtn.getScene().getWindow())).close();
        stageManager.showStage("../views/teacherAddForm.fxml", false, "New teacher");
    }

    @FXML
    void loadScheduleTable(Event event) {
        groupComboBox.getSelectionModel().select(null);
        scheduleMonthComboBox.getSelectionModel().select(null);
        scheduleYearComboBox.getSelectionModel().select(null);
        loadData();
    }

    @FXML
    void loadTeachersTable(Event event) {
        monthComboBox.getSelectionModel().select(null);
        yearComboBox.getSelectionModel().select(null);
        loadTeacherData();
    }

    @FXML
    void getGroupSchedule(MouseEvent event) {

    }

    @FXML
    void getTeacherSchedule(MouseEvent event) {
        editTeacherButton.setDisable(false);
        deleteTeacherButton.setDisable(false);
        teacherScheduleButton.setDisable(false);
    }

    @Override
    public void update() {
        loadTeacherData();
    }


    @FXML
    void deleteTeacher(ActionEvent event) {
        Teacher selected = teachersTable.getSelectionModel().getSelectedItem();
        teacherService.deleteEntity(selected);
    }

    @FXML
    void editTeacher(ActionEvent event) {
        teacherTransfer = new Teacher();
        teacherService.findById(teacherTransfer.getId());
        teacherTransfer = teachersTable.getSelectionModel().getSelectedItem();
        stageManager.showStage("../views/editTeacher.fxml", false, "Редактировать преодавателя");

    }

    @FXML
    void showGroupSchedule(ActionEvent event) {
        Groups selected = groupComboBox.getSelectionModel().getSelectedItem();
        if (selected.getGroupNo().equals("Все")) {
            loadData();
        } else {
            scheduleTable.getItems().clear();
            List<Schedule> temp = scheduleList.stream().filter(s -> s.getGroupNumber().equals(selected.getGroupNo())).collect(Collectors.toList());
            scheduleTable.getItems().setAll(temp);
        }
    }

    @FXML
    void showTeacherSchedule(ActionEvent event) {
        Teacher t = teachersTable.getSelectionModel().getSelectedItem();
        TeacherNameTransfer.getInstance().setTchr(t);
        //System.out.println(TeacherNameTransfer.getInstance().getName());
        stageManager.showStage("../views/teacherSchedule.fxml", false, "Teacher's schedule");
    }

    @FXML
    void findFree(ActionEvent event) {
        String month = monthComboBox.getSelectionModel().getSelectedItem();
        String year = yearComboBox.getSelectionModel().getSelectedItem();
        if (!(month.isEmpty() || year.isEmpty())) {
            List<Teacher> foundTeachers = teacherService.findFree(month + "-" + year);
            teachersTable.getItems().setAll(foundTeachers);
        } else {
            showAlert(new WarningAlert("Не указана дата"));
        }
    }

    @FXML
    void showStats(ActionEvent event) {
        groups = groupComboBox.getSelectionModel().getSelectedItem();
        if (groups != null && !groups.getGroupNo().equals("Все")) {
            stageManager.showStage("../views/stats.fxml", false, "Статистика");
        } else {
            showAlert(new WarningAlert("Выберите группу")).showAndWait();
        }
    }

    @FXML
    void findByMonth(ActionEvent event) {

    }

    @FXML
    void findByYear(ActionEvent event) {

    }

    @FXML
    void findSchedule(ActionEvent event) {
        List<Schedule> found = new ArrayList<>();
        String month = scheduleMonthComboBox.getSelectionModel().getSelectedItem();
        String year = scheduleYearComboBox.getSelectionModel().getSelectedItem();
        if (groupComboBox.getSelectionModel().getSelectedItem() != null) {
            if (groupComboBox.getSelectionModel().getSelectedItem().getGroupNo().equals("Все")) {
                showAlert(new WarningAlert("Выберете группу")).showAndWait();
            } else {
                found = scheduleService.findByGroupMonth(groupComboBox.getSelectionModel().getSelectedItem(),
                        month + "-" + year);
                scheduleTable.getItems().setAll(found);
            }
        }


    }

    @FXML
    void assignTeacher(ActionEvent event) {
        stageManager.showStage("../views/assignSubject.fxml", false, "Назначить преподавателя");
    }


    @FXML
    void editSchedule(ActionEvent event) {
        scheduleTransfer = scheduleTable.getSelectionModel().getSelectedItem();
        stageManager.showStage("../views/editClass.fxml", false, "Редактировать занятие");
    }

    @FXML
    void deleteSchedule(ActionEvent event) {
        boolean deleted = false;
        Schedule selectedSchedule = scheduleTable.getSelectionModel().getSelectedItem();
        Cycle cycle = selectedSchedule.getSubjectTeacherBySubjectTeacherId().getSubjectBySubjectId().getCycleByCycleId();
        Optional<Groups> group = groupService
                .findAll()
                .stream()
                .filter(g -> g.getGroupNo().equals(selectedSchedule.getGroupNumber()))
                .findFirst();
        Map<Cycle, Integer> cycleCount = scheduleService.countSubjects(group.get());
        cycleCount.put(cycle, cycleCount.get(cycle) - 1);
        List<CycleStats> cycleStats = new ArrayList<>();
        int sum = 0;
        for (Map.Entry<Cycle, Integer> entry : cycleCount.entrySet()) {
            sum += entry.getValue();
        }

        for (Map.Entry<Cycle, Integer> entry : cycleCount.entrySet()) {
            cycleStats.add(new CycleStats(entry.getKey(), entry.getValue(), new Double(entry.getValue()) / sum * 100));
        }
        for (CycleStats cs : cycleStats) {
            if (cs.getPercent() > 70) {
                Optional<ButtonType> res = showAlert(new ConfirmAlert("Если вы удалите даный предмет из расписания, то" +
                        " процент одного из циклов обучения превысит 70%")).showAndWait();
                if (res.get() == ButtonType.OK) {
                    scheduleService.delete(selectedSchedule.getId());
                    deleted = true;
                    break;
                } else {
                    return;
                }
            }
        }
        if (!deleted) {
            deleted = true;
            scheduleService.delete(selectedSchedule.getId());
        }

        if (deleted)
            showAlert(new SuccessAlert("Предмет успешно удален")).showAndWait();


    }
}
