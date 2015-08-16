package controller;

import javafx.application.Application;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import model.NodeIP;

/**
 * 
 * @author SARR Niébé / ADIB Ayoub
 *
 */


/**
 * 
 * @brief Notre classe principale: instanciation de notre environnement graphique javaFx et de notre controlleur principale:
 *
 */
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			TreeItem<NodeIP> rootTree = new TreeItem<NodeIP>(new NodeIP("Root (Localhost)", null));
		    Controller controller = new Controller(rootTree);
		    controller.start(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
