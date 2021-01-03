package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import View.Main;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import Model.DatabaseConnection;
import Model.FavouriteWord;
import Model.TexttoSpeech;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ControllerMyWord extends Application implements Initializable {
	@FXML
	JFXListView<String> listview;
	@FXML
	WebView webview;
	WebEngine web;
	@FXML
	JFXTextField txtfind;
	ObservableList<FavouriteWord> list;
	ControllerEngViet b = new ControllerEngViet();

	public void loaddata() throws SQLException {
		list = b.Upload();
		listview.getItems().addAll(FXCollections.observableArrayList(b.Update2()));
	}

	public void load() throws SQLException {
		listview.setItems(FXCollections.observableArrayList(b.Update2()));
		list = b.Upload();
	}

	int index;

	@FXML
	public void getSelected(MouseEvent event) {
		index = listview.getSelectionModel().getSelectedIndex();
		if (index <= -1) {
			return;
		}
		String s = listview.getItems().get(index);
		txtfind.setText(s);
		for (FavouriteWord i : list) {
			if (i.getWord().equals(s)) {
				web.loadContent(i.getDetail());
			}
		}
	}

	Connection connection;

	public void Delete() throws SQLException {
		Statement stat = connection.createStatement();
		String sql1 = "delete from [tbl-favourite] where account = N'" + Main.nowuser + "' and word ='"
				+ txtfind.getText() + "'";
		stat.executeUpdate(sql1);
		list = b.Upload();
		Reset();
		load();
	}

	public void search() throws SQLException {
		ObservableList<String> listString = Update1();
		FilteredList<String> filteredData = new FilteredList<>(listString, b -> true);
		txtfind.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(listview -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (listview.startsWith(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});
		SortedList<String> sortedData = new SortedList<>(filteredData);
		listview.setItems(sortedData);
	}

	public ObservableList<String> Update1() throws SQLException {
		Connection connection = DatabaseConnection.ConnectionData("Dictionary");
		ObservableList<String> list = FXCollections.observableArrayList();
		try {
			PreparedStatement ps1 = connection
					.prepareStatement("Select * from [tbl-favourite] where account = '" + Main.nowuser + "'");
			ResultSet rs = ps1.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(2));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	TexttoSpeech a = new TexttoSpeech();

	public void Speak() {
		a.ManVoice(txtfind.getText());
	}

	public void Speak2() {
		a.WomenVoice(txtfind.getText());
	}

	public void Reset() {
		txtfind.setText("");
		web.loadContent("");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			connection = DatabaseConnection.ConnectionData("Dictionary");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		web = webview.getEngine();
		try {
			loaddata();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			search();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/View/MyWord.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
