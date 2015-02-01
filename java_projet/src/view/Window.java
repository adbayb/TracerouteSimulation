package view;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import model.Ip;
import controller.Controller;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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

	static private final int WIDTH = 600;

	static private final int HEIGHT = 600;

	private Controller controller;
	
	//Views:
	private TreeGraph treeGraph;
	private TreeList treeList;
	//Control Boxes:
	private TextField ipField;
	private Button ipButton;
	private Button randomIpButton;
	private Button helpButton;
	//Sections Component:
	private BorderPane sectionsWindow;
	//Help Content:
	private static final String HELP_TEXT = 
		    "BLABLABLABLABLA1\n" +
		    "BLABLABLABLABLA2\n" +
		    "BLABLABLABLABLA3\n" +
		    "BLABLABLABLABLA4\n" +
		    "BLABLABLABLABLA5\n" +
		    "BLABLABLABLABLA6\n" +
		    "BLABLABLABLABLA7\n" +
		    "BLABLABLABLABLA8\n" +
		    "BLABLABLABLABLA9\n" +
		    "BLABLABLABLABLA10";
	
	//Class to redirect standard output (for example console log) to a TextArea javafx chart:
	public class ConsoleDebug extends OutputStream {
		private TextArea text;
		
		public ConsoleDebug(TextArea text) {
				this.text = text;
		}

		@Override
		public void write(int arg0) throws IOException {
			// redirects data to the text area javafx component:
				this.text.appendText(String.valueOf((char)arg0));
		}
	}
	

	public Window(Controller controller, TreeItem<Ip> rootTree) {
		//Views:
		this.controller = controller;
		treeGraph = new TreeGraph();
		treeList = new TreeList(rootTree);
		//Control Boxes:
		this.ipField = new TextField();
		this.ipButton = new Button("Validate");
		this.randomIpButton = new Button("Random IP");
		this.helpButton = new Button("Help Menu");
		//Sections Component:
		this.sectionsWindow = new BorderPane();
	}
	
	private VBox setControlViewBox() {
		//HBox équivalent à BoxLayout avec X_AXIS dans Swing:
		VBox vbox = new VBox();
		
		HBox hbox = new HBox();
		HBox.setHgrow(this.ipField, Priority.ALWAYS);
		this.ipField.setMaxWidth(Double.MAX_VALUE);
		//this.ipField.setMaxHeight(Double.MAX_VALUE);
		this.ipField.setPromptText("IP or HostName");
		this.helpButton.setMaxWidth(Double.MAX_VALUE);
		this.helpButton.setMaxHeight(100.00);
		
		hbox.getChildren().addAll(new Label("IP"),this.ipField,this.ipButton,this.randomIpButton);
		//Vbox
		vbox.setStyle("-fx-border-style: solid;"
		        + "-fx-border-color: black;"
		        + "-fx-border-width: 2;");
		//Espacement entre les éléments:
		vbox.setSpacing(20);
		//Padding css:
		vbox.setPadding(new Insets(10, 5, 0, 5));
		vbox.getChildren().addAll(hbox,this.helpButton);
		
		Label consoleLabel = new Label("Console Output");
		TextArea textArea = new TextArea();
		//15 colonnes afin de ne pas empiéter et réduire TreeGraph:
		textArea.setPrefColumnCount(15);
		PrintStream printStream = new PrintStream(new ConsoleDebug(textArea));
		System.setOut(printStream);
		System.setErr(printStream);
		vbox.getChildren().addAll(consoleLabel, textArea);
		textArea.toFront();
		
		Label helpContent = new Label(HELP_TEXT);
		
		if(this.helpButton != null) {
			this.helpButton.addEventHandler(ActionEvent.ACTION, event -> {
				if(vbox.getChildren().contains(helpContent)) {
					vbox.getChildren().remove(helpContent);
					//Nous mettons ensuite notre output console en dernier élément noeud graphique du parent (toBack() en premier élément):
					consoleLabel.toFront();
					textArea.toFront();
				}
				else
					vbox.getChildren().add(helpContent);
					consoleLabel.toFront();
					textArea.toFront();
			});
		}
	    
		return vbox;
	}
	
	private void setActionButtons() {
		if(this.ipButton != null) 
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
		
		this.setActionButtons();
		VBox vbox = this.setControlViewBox();
		
		final Label label = new Label("Traceroute JavaFX - ADIB Ayoub / SARR Niébé");
		label.setFont(new Font("Calibri", 25));
		//CSS styles (here padding, we can use instead setLabelPadding() function)):
		//label.setStyle("-fx-padding: 0px 0px 10px 0px;");
		label.setStyle("-fx-padding: 5px;");
		
		//this.verticalBox.listen();
		
		//Intégration de graphstream dans javafx:
		//sectionsWindow.setPrefSize(300,300); //borderPane size
		sectionsWindow.setTop(label);
		sectionsWindow.setRight(vbox); 
		sectionsWindow.setCenter(treeGraph.setLayout()); 
		//controlLayout.setRight(new Label("test")); 
		sectionsWindow.setBottom(treeList.setLayout());
		
		//Nous ajoutons tous nos éléments dans l'ordre dans notre super containeur root:
		root.getChildren().addAll(sectionsWindow);
		
		//on crée notre scène:
		fenetre.setScene(new Scene(root,WIDTH,HEIGHT));
		//on ajoute un titre à notre fenêtre
		fenetre.setTitle("Traceroute JavaFX - ADIB Ayoub / SARR Niébé");
		//On set une taille minimale de fenêtre pour une bonne ergonomie:
		fenetre.setMinHeight(HEIGHT/1.2);
		fenetre.setMinWidth(WIDTH/1.2);
		//on affiche notre fenêtre:
		//fenetre.hide();
		fenetre.show();
	}

	/*public void setController(Controller controller) {
		this.controller = controller;
	}*/
}
