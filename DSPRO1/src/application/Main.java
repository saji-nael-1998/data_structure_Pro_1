package application;

import java.io.File;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage mainStage) {
		// catching error
		try {
			Parent root = FXMLLoader.load(getClass().getResource("DepartmentChair.fxml"));
			mainStage.setScene(new Scene(root));
			mainStage.setTitle("Department");
			mainStage.show();
		} catch (Exception e) {
			System.out.println("Exception e:" + e);
		}

	}

	public static void main(String[] args) {
		launch(args);

	}

}
