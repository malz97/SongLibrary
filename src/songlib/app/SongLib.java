package songlib.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import songlib.view.Controller;

public class SongLib extends Application{
	@Override
	public void start(Stage primaryStage)
	throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				getClass().getResource("/songlib/view/test.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		Controller controller = loader.getController();
		controller.start(primaryStage);
		
		Scene scene = new Scene(root, 600, 450);
		primaryStage.setTitle("Song Library");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
