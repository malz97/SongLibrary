package songlib.view;

import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	public static final String FILE = "songs.csv";
	
	public void start(Stage mainStage) {
		//Creating an ObservableList from ArrayList
		//Loading songs from a csv file to obsList
		try {
			obsList = FXCollections.observableArrayList(SongUtility.loadToList(FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
		listView.setItems(obsList);
		//Initially select the first item in the list
		//Display song information
		if(!obsList.isEmpty()) {
			listView.getSelectionModel().select(0);
			edit.setDisable(false);
			delete.setDisable(false);
			showSong(mainStage);
		}
		//Adding a listener for the list view items
		listView
			.getSelectionModel()
			.selectedIndexProperty()
			.addListener(
					(obs, oldVal, newVal) ->
						showSong(mainStage));
		//List is empty at start, edit and delete not valid
		edit.setDisable(true);
		delete.setDisable(true);
	}
	
	private void showSong(Stage mainStage) {
		//Set text fields to blank if list is empty
		if(obsList.isEmpty()) {
			nameField.clear();
			artistField.clear();
			albumField.clear();
			yearField.clear();
			edit.setDisable(true);
			delete.setDisable(true);
			return;
		}
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
		if(result.orElse(null) == ButtonType.OK) {
			// User confirmed their action.
			String name, artist, album = "";
			int year = 0;
			name = nameField.getText();
			artist = artistField.getText();
			if(!albumField.getText().isEmpty()) {
				album = albumField.getText();
			}
			if(!yearField.getText().isEmpty()) {
				try {
					year = Integer.parseInt(yearField.getText());
				} catch (NumberFormatException nfe)  {
					Alert formatError = new Alert(AlertType.ERROR);
					formatError.setTitle("Error!");
					formatError.setHeaderText("Invalid format error!");
					formatError.setContentText("Input in year field not a number!");
					formatError.showAndWait();
					return;
				}
				if(year <= 0) {
					Alert negativeYear = new Alert(AlertType.ERROR);
					negativeYear.setTitle("Error!");
					negativeYear.setHeaderText("Invalid format error!");
					negativeYear.setContentText("Input in year field not a valid year!");
				}
			}
			Song s = new Song(name, artist, album, year);
			//Checks if song has name and artist
			if(s.getName().isEmpty() || s.getArtist().isEmpty()) {
				Alert invalid = new Alert(AlertType.ERROR);
				invalid.setTitle("Error!");
				invalid.setHeaderText("Invalid input error!");
				invalid.setContentText("Must enter song and artist name!");
				invalid.showAndWait();
				return;
			}
			//Checks for duplicates
			for(Song x : obsList) {
				if(x.equals(s)) {
					Alert duplicate = new Alert(AlertType.ERROR);
					duplicate.setTitle("Error!");
					duplicate.setHeaderText("Duplicate error!");
					duplicate.setContentText("Song is already contained in the library!");
					duplicate.showAndWait();
					return;
				}
			}
			//Song is added to list, and sort
			obsList.add(s);
			sortList();
			//Added song is automatically selected
			int index = obsList.indexOf(s);
			listView.getSelectionModel().select(index);
			//List is populated, edit and delete is valid
			delete.setDisable(false);
			edit.setDisable(false);
		} else {
			//User canceled their action.
			return;
		}
	}
	@FXML
	private void editSong(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("You clicked Edit.");
		alert.setContentText("Are you sure you'd like to edit this song's details? This action cannot be undone.");
		Optional<ButtonType> result = alert.showAndWait();
		Song old = listView.getSelectionModel().getSelectedItem();
		if(result.orElse(null) == ButtonType.OK) {
			// User confirmed their action.
			int index = listView.getSelectionModel().getSelectedIndex();
			Song s = obsList.get(index);
			s.setName(nameField.getText());
			s.setArtist(artistField.getText());
			if(s.getName().isEmpty() || s.getArtist().isEmpty()) {
				Alert invalid = new Alert(AlertType.ERROR);
				invalid.setTitle("Error!");
				invalid.setHeaderText("Invalid input error!");
				invalid.setContentText("Must enter song and artist name!");
				invalid.showAndWait();
				return;
			}
			if(!albumField.getText().isEmpty()) {
				s.setAlbum(albumField.getText());
			}
			//Checks if song has name and artist
			if(!yearField.getText().isEmpty()) {
				try {
					s.setYear(Integer.parseInt(yearField.getText()));
				} catch (NumberFormatException nfe)  {
					Alert formatError = new Alert(AlertType.ERROR);
					formatError.setTitle("Error!");
					formatError.setHeaderText("Invalid format error!");
					formatError.setContentText("Input in year field not a number!");
					formatError.showAndWait();
					return;
				}
				if(s.getYear() <= 0) {
					Alert negativeYear = new Alert(AlertType.ERROR);
					negativeYear.setTitle("Error!");
					negativeYear.setHeaderText("Invalid format error!");
					negativeYear.setContentText("Input in year field not a valid year!");
				}
			} else {
				s.setYear(0);
			}
			
			//Checks for duplicates
//			if(s.equals(old)) {
//					Alert duplicate = new Alert(AlertType.ERROR);
//					duplicate.setTitle("Error!");
//					duplicate.setHeaderText("Duplicate error!");
//					duplicate.setContentText("Song is already contained in the library!");
//					duplicate.showAndWait();
//					System.out.println(s.getArtist());
//					System.out.println(old.getArtist());
//					return;
//			}
			
			obsList.set(index, s);
			sortList();
		} else {
			// User canceled their action.
			return;
		}
	}
	@FXML
	private void deleteSong(ActionEvent e) {
		if(obsList.isEmpty()) {
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("You clicked Delete.");
		alert.setContentText("Are you sure you'd like to delete the selected song from your library? This action cannot be undone.");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.orElse(null) == ButtonType.OK) {
			// User confirmed their action.
			int index = listView.getSelectionModel().getSelectedIndex();
			obsList.remove(index);
			listView.getSelectionModel().select(index);
		} else {
			return;
		}
	}
	
	private void sortList() {
		obsList.sort((s1, s2) -> (s1.getName() + s1.getArtist()).compareToIgnoreCase(s2.getName() + s2.getArtist()));
	}
	
}
