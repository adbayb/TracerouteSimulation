package view;

import model.Ip;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * 
 * @author SARR Niébé / ADIB Noeud
 *
 */
public class View {

	static private final int WIDTH = 640;

	static private final int HEIGHT = 480;

	private Controller controller;

	private final TextField lastname;

	private final TextField firstname;
	
	private final TreeView<Ip> treeView;
	

	public View(TreeItem<Ip> rootTree) {
		treeView = new TreeView<Ip>(rootTree);
		lastname = new TextField();
		firstname = new TextField();
	}

	public void launch(Stage fenetre) throws Exception {
		StackPane root = new StackPane();
		
		//HBox équivalent à BoxLayout avec X_AXIS dans Swing:
		HBox hbox = new HBox();
		HBox.setHgrow(lastname, Priority.ALWAYS);
		HBox.setHgrow(firstname, Priority.ALWAYS);
		lastname.setMaxWidth(Double.MAX_VALUE);
		firstname.setMaxWidth(Double.MAX_VALUE);
		lastname.setPromptText("Last Name");
		firstname.setPromptText("First Name");
		Button button = new Button("add");
		
		//button.addEventHandler(ActionEvent.ACTION, controller.getAddStudentListener(lastname,firstname));
		//addAll permet d'ajouter n éléments à un containeur:
		hbox.getChildren().addAll(new Label("Lastname:"),lastname,new Label("Firstname:"),firstname,button);
		
		/*table.setEditable(true);
		TableColumn<Student, String> firstnameCol = new TableColumn<Student, String>("First Name");
		firstnameCol.setMinWidth(WIDTH/3);
		firstnameCol.setCellValueFactory(
				new PropertyValueFactory<Student, String>("firstname"));
		
		TableColumn<Student, String> lastnameCol = new TableColumn<Student, String>("Last Name");
		lastnameCol.setMinWidth(WIDTH/3);
		lastnameCol.setCellValueFactory(
				new PropertyValueFactory<Student, String>("lastname"));
		
		table.setItems(controller.getStudents());
		//System.out.println(table.getItems());
		table.getColumns().addAll(lastnameCol,firstnameCol);*/
		
		/*controller.addItem2Root(new Ip("Noeud0", "other"));
		controller.addItem2Root(new Ip("Noeud1", "other"));
		controller.addItem2Root(new Ip("Noeud2", "other"));
		controller.addItem2Root(new Ip("Noeud3", "other"));
		controller.addItem2Root(new Ip("Noeud4", "other"));
		controller.addItem2Root(new Ip("Noeud5", "other"));
		System.out.println(controller.addSearchedItem(new Ip("Noeud10", "other"),"Noeud1"));
		controller.addSearchedItem(new Ip("Noeud100", "other"),"Noeud10");*/
		controller.generate();
		
		final Label label = new Label("Traceroute JavaFX - SARR Niébé / ADIB Noeud");
		label.setFont(new Font("Arial", 20));
		
		//treeView = new TreeView<String>(controller.getTree());
		//treeView.setVisible(false);
		
		final VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(5, 0, 0, 1));
		vbox.getChildren().addAll(label, treeView, hbox);
		
		//Nous ajoutons tous nos éléments dans l'ordre dans notre super containeur root:
		root.getChildren().addAll(vbox);
		
		//on crée notre scène:
		fenetre.setScene(new Scene(root,WIDTH,HEIGHT));
		//on ajoute un titre à notre fenêtre
		fenetre.setTitle("mvc");
		//on affiche notre fenêtre:
		//fenetre.hide();
		fenetre.show();
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
}
