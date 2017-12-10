package com.university.schedule.Utils.alerts;

import javafx.scene.control.Dialog;
import org.springframework.stereotype.Component;

import javax.persistence.Column;

@Component
public interface Showable {
    Dialog show();
}
