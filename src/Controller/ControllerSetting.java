package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import View.Main;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class ControllerSetting extends Application implements Initializable {
	@FXML
	Hyperlink link;

	public void loaddata() {
		link.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getHostServices().showDocument("https://github.com/chukun09/Dictionary");
			}
		});
	}

	public void Logout() throws IOException {
		Main a = new Main();
		a.start(a.window);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loaddata();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/View/Setting.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
