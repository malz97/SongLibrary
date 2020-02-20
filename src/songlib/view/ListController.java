package songlib.view;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import songlib.app.Song;
import songlib.utility.SongUtility;

public class Controller {
	
	@FXML 
	private ListView<Song> listView;
	@FXML
	private Button add, edit, delete;
	@FXML
	private TextField nameField, artistField, albumField, yearField;
	
	
	private ObservableList<Song> obsList;
	
	public void start(Stage mainStage) {
		//Creating an ObservableList from ArrayList
		//Loading songs from a csv file to obsList
		obsList = FXCollections.observableArrayList(
				SongUtility.loadToList("songs-funky.csv"));
		listView.setItems(obsList);
		//Initially select the first item in the list
		listView.getSelectionModel().select(0);
		//Display song information
		showSong(mainStage);
		//Adding a listener for the list view items
		listView
			.getSelectionModel()
			.selectedIndexProperty()
			.addListener(
					(obs, oldVal, newVal) ->
						showSong(mainStage));
	}
	
	private void showSong(Stage mainStage) {
		Song s = listView.getSelectionModel().getSelectedItem();
		nameField.setText(s.getName());
		artistField.setText(s.getArtist());
		if(!s.getAlbum().equals("")) {
			albumField.setText(s.getAlbum());
		}else {
			albumField.clear();
			albumField.setPromptText("No year information");
		}
		if(s.getYear() > 0) {
			yearField.setText(String.valueOf(s.getYear()));
		}else {
			yearField.clear();
			yearField.setPromptText("No album information");
		}
	}
	
	
	@FXML
	private void addSong(ActionEvent e) {
		String name, artist;
		
	}
	@FXML
	private void editSong(ActionEvent e) {
		artistField.setText("Working!");
	}
	@FXML
	private void deleteSong(ActionEvent e) {
		albumField.setText("Working!");
	}
	
}
