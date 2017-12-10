package com.university.schedule.Utils.alerts;

import javafx.scene.control.Alert;

public class WarningAlert implements AlertThrower {

    String text;

    public WarningAlert(String text) {
        this.text = text;
    }

    @Override
    public Alert showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(text);
        alert.setHeaderText("Warning");
        alert.getDialogPane().setPrefSize(300,200);
        return alert;
    }
}
