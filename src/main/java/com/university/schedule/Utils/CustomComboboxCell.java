package com.university.schedule.Utils;

import javafx.scene.control.cell.ComboBoxListCell;

public class CustomComboboxCell<T> extends ComboBoxListCell<T> {

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            this.setText(null);
            this.setStyle("");
        } else {
            this.setText(item.toString());
            this.setGraphic(null);
        }
    }
}
