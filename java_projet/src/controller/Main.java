package controller;

import javafx.application.Application;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import model.Ip;

/**
 * 
 * @author SARR Niébé / ADIB Ayoub
 *
 */
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			TreeItem<Ip> rootTree = new TreeItem<Ip>(new Ip("Root (Localhost)", null));
		    //Controller controller = new Controller(tree, processus, view);
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
