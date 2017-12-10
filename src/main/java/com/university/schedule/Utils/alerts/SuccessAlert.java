package com.university.schedule.Utils.alerts;

import javafx.scene.control.Alert;

public class SuccessAlert implements AlertThrower {

    String text;

    public SuccessAlert(String text) {
        this.text = text;
    }

    @Override
    public Alert showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(text);
        alert.setHeaderText("Success");
        return alert;
    }
}
