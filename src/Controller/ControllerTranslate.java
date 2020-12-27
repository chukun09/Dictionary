package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Observer;
import java.util.ResourceBundle;
import org.json.JSONException;

import Model.TexttoSpeech;
import Model.Translate;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ControllerTranslate extends Application implements Initializable {
	@FXML
	private TextArea txteng, txtvie;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/View/TranslatePagraph.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	TexttoSpeech a = new TexttoSpeech();

	public void Speak() {
		a.WomenVoice(txteng.getText());
	}

	public void Speak2() {
		a.WomenVoice(txtvie.getText());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txteng.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				txtvie.setText(Translate.Translate(newValue));
			} catch (IOException | JSONException e) {
				e.printStackTrace();
			}
		});
	}
}