package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.json.JSONException;
import com.goxr3plus.speech.recognizer.GSpeechDuplex;
import com.goxr3plus.speech.recognizer.GSpeechResponseListener;
import com.goxr3plus.speech.recognizer.GoogleResponse;
import com.goxr3plus.speech.recognizer.Microphone;
import Model.TexttoSpeech;
import Model.Translate;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.sourceforge.javaflacencoder.FLACFileWriter;

public class ControllerTranslate extends Application implements Initializable, GSpeechResponseListener {
	@FXML
	private TextArea txteng, txtvie;
	@FXML
	FontAwesomeIconView micro, micro1;

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

	public void Micro() {
		final Microphone mic = new Microphone(FLACFileWriter.FLAC);
		GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
		duplex.setLanguage("en");
		if (micro.getFill().equals(Color.WHITE)) {
			micro.setFill(Color.RED);
			new Thread(() -> {
				try {
					duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());
				} catch (Exception ex) {
				}

			}).start();
		} else {
			micro.setFill(Color.WHITE);
			mic.close();
			duplex.stopSpeechRecognition();
		}
		duplex.addResponseListener(new GSpeechResponseListener() {
			String old_text = "";
			public void onResponse(GoogleResponse gr) {
				String output = "";
				output = gr.getResponse();
				if (gr.getResponse() == null) {
					this.old_text = txteng.getText();
					if (this.old_text.contains("(")) {
						this.old_text = this.old_text.substring(0, this.old_text.indexOf('('));
					}
					System.out.println("Paragraph Line Added");
					this.old_text = (txteng.getText() + "\n");
					this.old_text = this.old_text.replace(")", "").replace("( ", "");
					txteng.setText(this.old_text);
					return;
				}
				if (output.contains("(")) {
					output = output.substring(0, output.indexOf('('));
				}
				if (!gr.getOtherPossibleResponses().isEmpty()) {
					output = output + " (" + (String) gr.getOtherPossibleResponses().get(0) + ")";
				}
				System.out.println(output);
				txteng.setText("");
				txteng.appendText(this.old_text);
				txteng.appendText(output);
			}
		});
	}
	public void Micro2() {
		final Microphone mic = new Microphone(FLACFileWriter.FLAC);
		GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
		duplex.setLanguage("vi");
		if (micro1.getFill().equals(Color.WHITE)) {
			micro1.setFill(Color.RED);
			new Thread(() -> {
				try {
					duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());
				} catch (Exception ex) {
				}

			}).start();
		} else {
			micro1.setFill(Color.WHITE);
			mic.close();
			duplex.stopSpeechRecognition();
		}
		duplex.addResponseListener(new GSpeechResponseListener() {
			String old_text = "";
			public void onResponse(GoogleResponse gr) {
				String output = "";
				output = gr.getResponse();
				if (gr.getResponse() == null) {
					this.old_text = txteng.getText();
					if (this.old_text.contains("(")) {
						this.old_text = this.old_text.substring(0, this.old_text.indexOf('('));
					}
					System.out.println("Paragraph Line Added");
					this.old_text = (txteng.getText() + "\n");
					this.old_text = this.old_text.replace(")", "").replace("( ", "");
					txteng.setText(this.old_text);
					return;
				}
				if (output.contains("(")) {
					output = output.substring(0, output.indexOf('('));
				}
				if (!gr.getOtherPossibleResponses().isEmpty()) {
					output = output + " (" + (String) gr.getOtherPossibleResponses().get(0) + ")";
				}
				System.out.println(output);
				txteng.setText("");
				txteng.appendText(this.old_text);
				txteng.appendText(output);
			}
		});
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

	@Override
	public void onResponse(GoogleResponse gr) {

	}
}