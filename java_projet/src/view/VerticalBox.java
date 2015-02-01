package view;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

//Layout View
//Contains other items, options into vbox layout (which can contain other layout like hbox, stackpane etc...):
public class VerticalBox {
	private VBox vbox;
	private HBox hbox;
	private TextField ipField;
	private Button ipButton;
	private boolean test;
	
	private Controller controller;
	private TreeGraph graph;
	

	public VerticalBox(Controller controller, TreeGraph graph) {
		this.vbox = new VBox();
		this.hbox = new HBox();
		this.ipField = new TextField();
		this.ipButton = new Button("Validate");
		
		this.controller = controller;
		this.graph = graph;
		this.test = true;
	}
	
	public VBox getLayout() {
		
		return this.vbox;
	}
	
	public VBox setLayout() {
		//HBox équivalent à BoxLayout avec X_AXIS dans Swing:
		HBox.setHgrow(this.ipField, Priority.ALWAYS);
		//HBox.setHgrow(firstname, Priority.ALWAYS);
		this.ipField.setMaxWidth(Double.MAX_VALUE);
		this.ipField.setPromptText("IP or HostName");
		Button randomIpButton = new Button("Random IP");
		//this.ipButton.addEventHandler(ActionEvent.ACTION, event -> {this.controller.generate(this.graph.getView(),"192.168.1.21");});
		ipButton.addEventHandler(ActionEvent.ACTION, event -> {ipButton.setDisable(true);if(this.controller.generate(this.graph.getView(),"192.168.1.21")) ipButton.setDisable(false);});
		
		this.hbox.getChildren().addAll(new Label("IP"),this.ipField,this.ipButton,randomIpButton);
		//Vbox
		this.vbox.setSpacing(10);
		this.vbox.setPadding(new Insets(5, 0, 0, 1));
		this.vbox.getChildren().addAll(hbox);
	    
		return this.vbox;
	}
	
	public boolean changeLayout(VBox box) {
		if(box != null) {
			this.vbox = box;
			
			return true;
		}
		
		return false;
	}
	
	public void listen() {
		System.out.println(this.test);
		this.ipButton.addEventHandler(ActionEvent.ACTION, event -> {if(this.test == true) {this.test=this.controller.generate(this.graph.getView(),"192.168.1.21");}});
		
		return;
	}
}
