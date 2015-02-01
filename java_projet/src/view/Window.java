package view;

import model.Ip;
import controller.Controller;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
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
public class Window {

	static private final int WIDTH = 800;

	static private final int HEIGHT = 800;

	private Controller controller;
	
	//Views:
	private final TreeGraph treeGraph;
	private final TreeList treeList;
	//Control Boxes:
	private TextField ipField;
	private Button ipButton;
	private Button randomIpButton;
	

	public Window(Controller controller, TreeItem<Ip> rootTree) {
		//Views:
		this.controller = controller;
		treeGraph = new TreeGraph();
		treeList = new TreeList(rootTree);
		//Control Boxes:
		this.ipField = new TextField();
		this.ipButton = new Button("Validate");
		this.randomIpButton = new Button("Random IP");
	}
	
	private VBox setControlViewBox() {
		//HBox équivalent à BoxLayout avec X_AXIS dans Swing:
		VBox vbox = new VBox();
		
		HBox hbox = new HBox();
		HBox.setHgrow(this.ipField, Priority.ALWAYS);
		//HBox.setHgrow(firstname, Priority.ALWAYS);
		this.ipField.setMaxWidth(Double.MAX_VALUE);
		this.ipField.setPromptText("IP or HostName");
		
		hbox.getChildren().addAll(new Label("IP"),this.ipField,this.ipButton,this.randomIpButton);
		//Vbox
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(5, 0, 0, 1));
		vbox.getChildren().addAll(hbox);
	    
		return vbox;
	}
	
	private void setActionButtons() {
		if(this.ipButton != null) 
			//ipButton.addEventHandler(ActionEvent.ACTION, event -> {ipButton.setDisable(true);boolean tmp=false;System.out.println(tmp);tmp = this.controller.generate(treeGraph.getView(),ipField.getText()); ipButton.setDisable(false);System.out.println(ipField.getText()+tmp);});
			this.ipButton.addEventHandler(ActionEvent.ACTION, event -> {
				//ipButton.setDisable(true); to avoid multi-clicks:
				this.ipButton.setDisable(true);
				this.controller.generate(treeGraph.getView(),ipField.getText()); 
				this.ipButton.setDisable(false);
			});
		
		if(this.randomIpButton != null) {
			this.randomIpButton.addEventHandler(ActionEvent.ACTION, event -> {
				String ipHostname = null;
				
				//ipButton.setDisable(true); to avoid multi-clicks:
				this.randomIpButton.setDisable(true);
				if((ipHostname = this.controller.randomizeIPHostname(0,255)) != null) {
					//System.out.println(ipHostname); 
					this.controller.generate(treeGraph.getView(),ipHostname); 
				}
				this.randomIpButton.setDisable(false);
			});
		}
		
		return;
	}

	public void launch(Stage fenetre) throws Exception {
		StackPane root = new StackPane();
		
		//button.addEventHandler(ActionEvent.ACTION, controller.getAddStudentListener(lastname,firstname));
		//addAll permet d'ajouter n éléments à un containeur:
		
		
		//DEBUG: Fonction générale de notre modèle Tree (Noeud):
		/*ObservableList<TreeItem<Ip>> tmp = FXCollections.observableList(new ArrayList<TreeItem<Ip>>());
		tmp.add(controller.getRoot());
		ObservableList<TreeItem<Ip>> tmp2 = FXCollections.observableList(new ArrayList<TreeItem<Ip>>());
		ObservableList<TreeItem<Ip>> tmp3 = FXCollections.observableList(new ArrayList<TreeItem<Ip>>());
		ObservableList<TreeItem<Ip>> tmp4 = FXCollections.observableList(new ArrayList<TreeItem<Ip>>());
		controller.addItem2Root(new Ip("Noeud0", tmp));
		controller.addItem2Root(new Ip("Noeud1", tmp));
		controller.addItem2Root(new Ip("Noeud2", tmp));
		controller.addItem2Root(new Ip("Noeud3", tmp));
		controller.addItem2Root(new Ip("Noeud4", tmp));
		controller.addItem2Root(new Ip("Noeud5", tmp));
		tmp2.add(controller.search(controller.getRoot(),"Noeud0"));
		tmp2.add(controller.search(controller.getRoot(),"Noeud1"));
		controller.addSearchedItem(new Ip("Noeud10", tmp2),"Noeud1");
		tmp3.add(controller.search(controller.getRoot(),"Noeud10"));
		controller.addSearchedItem(new Ip("Noeud100", tmp3),"Noeud10");
		tmp4.add(controller.search(controller.getRoot(),"Noeud100"));
		controller.addSearchedItem(new Ip("Noeud1000", tmp4),"Noeud100");
		System.out.println(controller.search(controller.getRoot(),"Noeud10000"));
		//System.out.println(controller.searchInterval(controller.search(controller.getRoot(),"Noeud100"),controller.search(controller.getRoot(),"Noeud10000"),"Noeud100"));
		//System.out.println(controller.getParents(controller.search(controller.getRoot(),"Noeud100"),"other"));
		controller.generateSingleGraph(treeGraph.getView());*/
		
		//controller.generate(treeGraph.getView(), "192.168.1.16");
		//controller.generate(treeGraph.getView(), "192.168.1.16");
		
		this.setActionButtons();
		VBox vbox = this.setControlViewBox();
		
		final Label label = new Label("Traceroute JavaFX - SARR Niébé / ADIB Noeud");
		label.setFont(new Font("Arial", 20));
		
		//this.verticalBox.listen();
		
		//Intégration de graphstream dans javafx:
		
		final BorderPane controlLayout = new BorderPane();  //Set the size of the BorderPane and show its borders //by making them black 
		//controlLayout.setPrefSize(300,300); 
		//controlLayout.setStyle("-fx-border-color: black;");
		controlLayout.setTop(label); 
		//controlLayout.setLeft(swingNode); 
		controlLayout.setRight(vbox); 
		//controlLayout.setRight(verticalBox.setLayout()); 
		controlLayout.setCenter(treeGraph.setLayout()); 
		controlLayout.setBottom(treeList.setLayout());
		
		//Nous ajoutons tous nos éléments dans l'ordre dans notre super containeur root:
		root.getChildren().addAll(controlLayout);
		
		//on crée notre scène:
		fenetre.setScene(new Scene(root,WIDTH,HEIGHT));
		//on ajoute un titre à notre fenêtre
		fenetre.setTitle("mvc");
		//on affiche notre fenêtre:
		//fenetre.hide();
		fenetre.show();
	}

	/*public void setController(Controller controller) {
		this.controller = controller;
	}*/
}
