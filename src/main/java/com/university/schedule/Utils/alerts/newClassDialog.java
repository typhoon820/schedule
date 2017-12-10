//package com.university.schedule.Utils.alerts;
//
//import com.university.schedule.model.Schedule;
//import com.university.schedule.model.SubjectTeacher;
//import com.university.schedule.service.ScheduleService;
//import com.university.schedule.service.SubjectTeacherService;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.Dialog;
//import javafx.scene.control.Label;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class newClassDialog implements Showable {
//
//    @Autowired
//    ScheduleService scheduleService;
//
//    @Autowired
//    SubjectTeacherService subjectTeacherService;
//
//
//    @Override
//    public Dialog show() {
//        Dialog<ButtonType> dialog = new Dialog<>();
//        dialog.setTitle("Добавить новое занятие");
//        dialog.setHeaderText("Введите информацию о новом занятии");
//        dialog.setResizable(false);
//        dialog.getDialogPane().setPrefSize(300,500);
//        Label date = new Label("Дата: ");
//        Label time = new Label("Время: ");
//        Label group = new Label("Группа: ");
//        Label room = new Label("Аудитория: ");
//        Label subject = new Label("Предмет: ");
//        Label teacher = new Label("Преподаватель: ");
//        Label exam = new Label("Вид отчетности: ");
//
//        //Label album = new Label("Album: ");
//        TextField songNameText = new TextField();
//        ComboBox<String> comboBox = new ComboBox<>();
//        ObservableList list =  FXCollections.observableArrayList();
//        genreService.findAll().stream().forEach(e->list.add(e.getGenre()));
//        comboBox.setItems(list);
//        comboBox.setPromptText("genre...");
//        GridPane gridPane = new GridPane();
//        gridPane.add(songName, 1,1);
//        gridPane.add(songNameText, 2,1);
//        gridPane.add(genre,1,2);
//        gridPane.add(comboBox,2,2);
//        dialog.getDialogPane().setContent(gridPane);
//        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
//        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
//        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
//        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
//
//        dialog.setResultConverter(buttonType -> {
//            if(buttonType==buttonTypeOk){
//                SongEntity s = new SongEntity();
//                s.setName(songNameText.getText());
//                s.setGenre(genreService.findByName(comboBox.getSelectionModel().getSelectedItem()));
//                return s;
//            }
//            return null;
//        });
//
//        return dialog;
//    }
//}
