package songlib.view;

import songlib.app.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;


public class ListController {
	
	@FXML Button addBtn, deleteBtn, editBtn;
	@FXML Text songNameInfo, artistInfo, albumInfo, yearInfo;
	@FXML TextField songNameField, artistField, albumField, yearField;
	@FXML ListView<Song> listView;
	
	private ObservableList<Song> obsList;
	
	public void start(Stage mainStage) {
		//Creating an ObservableList from ArrayList
		obsList = FXCollections.observableArrayList();
		//** Need function to load songs from .txt file **//
		listView.setItems(obsList);
		//Initially select the first item in the list
		listView.getSelectionModel().select(0);
		//Setting the listener for the items
		listView
			.getSelectionModel()
			.selectedIndexProperty()
			.addListener(
					(obs, oldVal, newVal) ->
					showSongDetail());
	
	}
	
	private void showSongDetail() {
		Song s = listView.getSelectionModel().getSelectedItem();
		if (s == null) {
			return;
		}
		//Display the selected song's information
		songNameInfo.setText(s.getName());
		artistInfo.setText(s.getArtist());
		//Song name and artist are required at minimum
		if(!s.getAlbum().equals("")) {
			albumInfo.setText(s.getAlbum());
		}
		if(s.getYear() == 0) {
			yearInfo.setText(Integer.toString(s.getYear()));
		}
		//Input fields for adding are blank by default
		songNameField.setText("");
		artistField.setText("");
		albumField.setText("");
		yearField.setText("");
	}
	
	
	
	
	
	
}
