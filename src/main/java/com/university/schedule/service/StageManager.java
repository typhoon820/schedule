package com.university.schedule.service;

import com.university.schedule.configuration.SpringFxmlLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageManager {
    private SpringFxmlLoader loader = SpringFxmlLoader.getLoader();
    private static StageManager manager = null;

    public SpringFxmlLoader getLoader() {
        return loader;
    }

    private StageManager(){
    }

    public static StageManager getInstance(){
        if (manager == null){
            manager = new StageManager();
        }
        return manager;
    }

    public Stage showStage(String url, boolean resizable, String title){
        Parent parent = (Parent) loader.load(url);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle(title);
        stage.setScene(new Scene(parent));
        stage.setResizable(resizable);
        stage.show();
        return stage;
    }
}
