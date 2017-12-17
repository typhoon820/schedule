package com.university.schedule.controller;

import com.university.schedule.Utils.alerts.AlertThrower;
import com.university.schedule.Utils.alerts.Showable;
import com.university.schedule.model.Groups;
import com.university.schedule.model.Schedule;
import com.university.schedule.model.Teacher;
import com.university.schedule.service.StageManager;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractController {
    protected StageManager stageManager = StageManager.getInstance();
    protected static Groups groups;
    protected static Teacher teacherTransfer;
    protected static Schedule scheduleTransfer;
    protected Alert showAlert(AlertThrower at){
        return at.showAlert();
    }
    protected Dialog showDialog(Showable sh){
        return sh.show();
    }
}
