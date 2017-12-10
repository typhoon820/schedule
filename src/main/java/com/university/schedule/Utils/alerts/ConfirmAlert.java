package com.university.schedule.Utils.alerts;

import javafx.scene.control.Alert;

public class ConfirmAlert implements AlertThrower {

    String text;

    public ConfirmAlert(String text) {
        this.text = text;
    }

    @Override
    public Alert showAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setContentText(text);
        alert.setHeaderText("Требуется подтверждение");
        alert.getDialogPane().setPrefSize(300,200);
        return alert;
    }
}
