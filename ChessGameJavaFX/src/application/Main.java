package application;
import java.awt.Image;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {  
	
	public static boolean isWhiteTurn = true;
	
	@Override
	public void start(Stage stage) {
		try {
			Parent root = (Parent) FXMLLoader.load(getClass().getResource("/application/ChessGameFX.fxml"));

			Scene scene = new Scene(root);

			stage.setTitle("Chess");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
			
			// close application on termination
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
				}
			});
			
			//stage.close();
						
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
    public static void main(String[] args) {
    	
    	launch(args);

    } 
}
