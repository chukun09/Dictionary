package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import Model.*;
import View.Main;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ControllerEngViet extends Application implements Initializable {
	@FXML
	FontAwesomeIconView heart;
	@FXML
	JFXTextField combofind;
	WebEngine web;
	@FXML
	JFXListView<String> listword, listviewundo;
	@FXML
	WebView webview;
	Connection connection;
	ResultSet rs;
	static ObservableList<FavouriteWord> favouriteWord;
	ObservableList<Word> list;

	public void loaddata() throws SQLException {
		favouriteWord = Upload();
		list = Update();
		for (Word i : list) {
			listword.getItems().add(i.getWord());
		}
		web = webview.getEngine();
	}


	public void Click() throws SQLException {
		Statement stat = connection.createStatement();
		if (heart.getFill() == Color.RED) {
			String sql1 = "delete from [tbl-favourite] where account = N'" + Main.nowuser + "' and word ='"
					+ combofind.getText() + "'";
			stat.executeUpdate(sql1);
			favouriteWord = Upload();
			heart.setFill(Color.WHITE);
			return;
		} else if (combofind.getText().equals("")) {
			Notice("Vui lòng chọn từ !");
			return;
		}
		try {

			for (Word i : list) {
				if (i.getWord().equals(combofind.getText())) {
					String sql = "Insert into [tbl-favourite] (account, word, detail) values ( N'" + Main.nowuser
							+ "', N'" + i.getWord().replace("'", "\\'") + "', N'" + i.getDetail().replace("'", "\\'") + "')";
					stat.executeUpdate(sql);
					heart.setFill(Color.RED);
					favouriteWord = Upload();
				}
			}
		} catch (Exception e) {
			Notice("Từ đang bảo trì không thể thêm vào yêu thích !!!");
		}
	}

	public ObservableList<FavouriteWord> Upload() throws SQLException {
		connection = DatabaseConnection.ConnectionData("Dictionary");
		ObservableList<FavouriteWord> list = FXCollections.observableArrayList();
		try {
			PreparedStatement ps1 = connection.prepareStatement("Select * from [tbl-favourite]");
			rs = ps1.executeQuery();
			while (rs.next()) {
				list.add(new FavouriteWord(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
		} catch (Exception e) {
		}
		return list;
	}

	public void Notice(String s) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông Báo");
		alert.setHeaderText(null);
		alert.setContentText(s);
		alert.showAndWait();
	}

	public ObservableList<Word> Update() throws SQLException {
		connection = DatabaseConnection.ConnectionData("Dictionary");
		ObservableList<Word> list1 = FXCollections.observableArrayList();
		try {
			PreparedStatement ps1 = connection.prepareStatement("Select * from tbl_edict");
			rs = ps1.executeQuery();
			while (rs.next()) {
				list1.add(new Word(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list1;
	}

	public ObservableList<String> Update1() throws SQLException {
		connection = DatabaseConnection.ConnectionData("Dictionary");
		ObservableList<String> list = FXCollections.observableArrayList();
		try {
			PreparedStatement ps1 = connection.prepareStatement("Select * from tbl_edict");
			rs = ps1.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(2));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	public ObservableList<String> Update2() throws SQLException {
		connection = DatabaseConnection.ConnectionData("Dictionary");
		ObservableList<String> list = FXCollections.observableArrayList();
		try {
			PreparedStatement ps1 = connection.prepareStatement("Select * from [tbl-favourite] where account = '" + Main.nowuser + "'");
			rs = ps1.executeQuery();
			while (rs.next()) {
					list.add(rs.getString(2));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	public void Reset() {
		combofind.setText("");
		web.loadContent(null);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loaddata();
			search();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void search() throws SQLException {
		ObservableList<String> list = Update1();
		FilteredList<String> filteredData = new FilteredList<>(list, b -> true);
		combofind.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(listword -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (listword.startsWith(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});
		SortedList<String> sortedData = new SortedList<>(filteredData);
		listword.setItems(sortedData);
	}

	int index = -1;

	@FXML
	void getSelected(MouseEvent event) throws SQLException {
		index = listword.getSelectionModel().getSelectedIndex();
		if (index <= -1) {
			return;
		}
		String s = listword.getItems().get(index);
		combofind.setText(s);
		for (FavouriteWord i : favouriteWord) {
			if (i.getWord().equals(s) && i.getAccount().trim().equals(Main.nowuser)) {
				heart.setFill(Color.RED);
				break;
			} else
				heart.setFill(Color.WHITE);
		}
		listviewundo.getItems().add(s);
		ObservableList<Word> list = Update();
		for (Word i : list) {
			if (i.getWord().equals(s)) {
				web.loadContent(i.getDetail());
			}
		}
	}

	TexttoSpeech a = new TexttoSpeech();

	public void Speak() {
		a.WomenVoice(combofind.getText());
	}

	public void Speak2() {
		a.ManVoice(combofind.getText());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/View/EngVietDictionary.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
