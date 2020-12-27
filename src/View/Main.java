package View;

import java.sql.Statement;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import Controller.MainController;
import Model.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Main extends Application {
	@FXML
	private JFXPasswordField txtpass, txtconfirm, txtpassword;
	@FXML
	private CheckBox checkbox;
	@FXML
	private JFXTextField txttdn;
	public static String nowuser;
	@FXML
	private TextField txtacc;
	public static Stage window, window2;
	public static Scene scene2;
	public static Parent root2, root, root3;

	@Override
	public void start(Stage primaryStage) throws IOException {
		window = primaryStage;
		window2 = new Stage();
		root = FXMLLoader.load(getClass().getResource("/View/Register.fxml"));
		root2 = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
		scene2 = new Scene(root);
		window.setScene(new Scene(root2));
		window.show();
	}

	public void Login(ActionEvent event) throws IOException, SQLException {
		if (check() == false) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông Báo");
			alert.setHeaderText(null);
			alert.setContentText("Nhập sai tài khoản hoặc tài khoản không tồn tại :(((");
			alert.showAndWait();
		} else {
			nowuser = txttdn.getText();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/App.fxml"));
			root3 = loader.load();
			MainController a = loader.getController();
			a.Controllabel("Welcome " + nowuser + "!");
			window.close();
			window.setScene(new Scene(root3));
			window.show();
		}
	}

	public boolean check() throws SQLException {
		Connection connection = DatabaseConnection.ConnectionData("Dictionary");
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery("SELECT * FROM TaiKhoan");
		while (rs.next()) {
			if (txtpassword.getText().equals(rs.getString(2).toString())
					&& txttdn.getText().equals(rs.getString(1).toString())) {
				return true;
			}
		}
		return false;
	}

	public void Register() {
		window2.setScene(scene2);
		window2.show();
	}

	public void Regis() throws SQLException {
		Connection connection = DatabaseConnection.ConnectionData("Dictionary");
		Statement stat = connection.createStatement();
		if (!txtpass.getText().equals(txtconfirm.getText())) {
			Notice("Mật Khẩu không trùng khớp !!!");
		} else if (!checkbox.isSelected()) {
			Notice("Vui lòng đồng ý với điều khoản sử dụng !!!");
		} else {
			try {
				String s = "Insert into TaiKhoan(Account, Password) values (N'" + txtacc.getText() + "' ,N'"
						+ txtpass.getText() + "')";
				stat.executeUpdate(s);
			} catch (Exception e) {
				Notice("Tài Khoản đã tồn tại !");
				return;
			}
			Notice("Register Completed !");
			txtacc.clear();
			txtconfirm.clear();
			txtpass.clear();
			window2.close();
		}
	}

	public void Notice(String s) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông Báo");
		alert.setHeaderText(null);
		alert.setContentText(s);
		alert.showAndWait();
	}

	public static void main(String[] args) throws SQLException {
		launch(args);
	}

}
