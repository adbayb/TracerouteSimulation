package controller;

import javafx.application.Application;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import model.Ip;
import model.Tree;
import view.View;

/**
 * 
 * @author SARR Niébé / ADIB Ayoub
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			TreeItem<Ip> rootTree = new TreeItem<Ip>(new Ip("Root", "Traceroute"));
			Tree tree = new Tree(rootTree);
		    View view = new View(rootTree);
		    //Processus processus = new Processus();
		    //Controller controller = new Controller(tree, processus, view);
		    Controller controller = new Controller(tree, view);
		    controller.start(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
