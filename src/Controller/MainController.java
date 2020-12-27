package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class MainController {
	@FXML
	Pane pane1, pane2, pane3, pane4;
	@FXML
	Label welcome;

	public void Controllabel(String s) {
		welcome.setText(s);
	}

	public void Click1() {
		pane1.setVisible(true);
		pane2.setVisible(false);
		pane3.setVisible(false);
		pane4.setVisible(false);
	}

	public void Click2() {
		pane2.setVisible(true);
		pane1.setVisible(false);
		pane3.setVisible(false);
		pane4.setVisible(false);
	}

	public void Click3() {
		pane3.setVisible(true);
		pane2.setVisible(false);
		pane1.setVisible(false);
		pane4.setVisible(false);
	}
	public void Click4() {
		pane3.setVisible(false);
		pane2.setVisible(false);
		pane1.setVisible(false);
		pane4.setVisible(true);
	}
}
