package com.university.schedule.Utils;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;

public class CustomTableCell<S, T> extends TableCell<S,T> {

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            this.setText(null);
            this.setStyle("");
        } else {
            if (item instanceof String) this.setText((String)item);
            this.setWrapText(true);
            //this.setPrefSize(120,60);
            this.setPrefHeight(60);
            this.setGraphic(null);
        }
    }
}
