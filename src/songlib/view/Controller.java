package songlib.view;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.ImageInput;
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
				SongUtility.loadToList("src/songlib/utility/songs.csv"));
		listView.setItems(obsList);
		//Initially select the first item in the list
		listView.getSelectionModel().select(0);
		//Display song information
		showSong();
		//Adding a listener for the list view items
		listView
			.getSelectionModel()
			.selectedIndexProperty()
			.addListener(
					(obs, oldVal, newVal) ->
						showSong());
	}
	
	private void showSong() {
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
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("You clicked Add.");
		alert.setContentText("Are you sure you'd like to add this song to your library?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			// User confirmed their action.
			String name, artist, album;
			int year;
			name = nameField.getText();
			artist = artistField.getText();
			album = albumField.getText();
			if(!yearField.getText().isEmpty()) {
				year = Integer.parseInt(yearField.getText());
				// TODO: Check if yearField contains only numbers.
			} else {
				year = 0;
			}
			Song s = new Song(name, artist, album, year);
			obsList.add(s);
		} else {
			// User canceled their action.
			showSong();
		}
	}
	@FXML
	private void editSong(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("You clicked Edit.");
		alert.setContentText("Are you sure you'd like to edit this song's details? This action cannot be undone.");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			// User confirmed their action.
			int index = listView.getSelectionModel().getSelectedIndex();
			Song s = obsList.get(index);
			s.setName(nameField.getText());
			s.setArtist(artistField.getText());
			if(!albumField.getText().isEmpty()) {
				s.setAlbum(albumField.getText());
			}
			if(!yearField.getText().isEmpty()) {
				s.setYear(Integer.parseInt(yearField.getText()));
			} else {
				s.setYear(0);
			}
			obsList.set(index, s);
		} else {
			// User canceled their action.
			showSong();
		}
	}
	@FXML
	private void deleteSong(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("You clicked Delete.");
		alert.setContentText("Are you sure you'd like to delete the selected song from your library? This action cannot be undone.");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			// User confirmed their action.
			int index = listView.getSelectionModel().getSelectedIndex();
			obsList.remove(index);
		} else {
			// User canceled their action.
		}
	}
	
}