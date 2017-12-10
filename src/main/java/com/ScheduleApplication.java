package com;

import com.university.schedule.configuration.SpringFxmlLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScheduleApplication extends AbstractJavaFXApplication{

	private SpringFxmlLoader loader = SpringFxmlLoader.getLoader();

	@Override
	public void start(Stage stage) throws Exception {


		Parent root = (Parent)loader.load("../views/main.fxml");
		stage.setTitle("Main Table");
		stage.setScene(new Scene(root, 920, 550));
		stage.show();
		stage.setResizable(false);
	}

	public static void main(String[] args) {
		launchApp(ScheduleApplication.class, args);
	}
}
