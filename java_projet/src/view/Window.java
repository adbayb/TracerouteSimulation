package view;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.NodeIP;
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
 * @author SARR Ni�b� / ADIB Noeud
 *
 */

//Notre classe principale d�finissant le GUI:
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
	private VBox vBox;
	//Help Content:
	private static final String HELP_TEXT = 
		    "Ce programme Java permet de tracer les chemins\n" +
		    "possibles d'un IP a vers un IP b. Le GUI contient:\n" +
		    "\n" +
		    "Champs IP: indiquer votre ip ou hostname de\n" +
		    "destination.\n" +
		    "Bouton Validate: lance le traitement avec l'IP saisi\n" +
		    "Bouton Random IP: Génére un IP aléatoire et\n" +
		    "lance le traitement\n" +
		    "Progress Bar: indique l'avancement des traitements\n" +
		    "Arbre graphique (A gauche) | Liste (en bas)";
	private String ipHostname;
	
	//Classe interne pour rediriger la sortie standard vers TextArea Javafx:
	/*public class ConsoleDebug extends OutputStream {
		private TextArea text;
		
		public ConsoleDebug(TextArea text) {
				this.text = text;
		}

		@Override
		public void write(int arg0) throws IOException {
			// redirects data to the text area javafx component:
			this.text.appendText(String.valueOf((char)arg0));
		}
	}*/
	

	/**
	 * @brief Constructeur de la fen�tre (charge le controller, le graphe et l'arbre)
	 * @param controller : Controller de l'application
	 * @param rootTree : Arbre qui va �tre charg� en fonction des Ip
	 */
	public Window(Controller controller, TreeItem<NodeIP> rootTree) {
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
		this.vBox = new VBox();
		this.ipHostname = new String();
	}
	
	/**
	 * @brief 
	 * @param vBox
	 */
	private void setControlViewBox(VBox vBox) {
		VBox vbox = vBox;
		//HBox �quivalent � BoxLayout avec X_AXIS dans Swing
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
		//Espacement entre les �l�ments:
		vbox.setSpacing(20);
		//Padding css:
		vbox.setPadding(new Insets(10, 5, 0, 5));
		//inclusion des �l�ments dans notre vbox:
		vbox.getChildren().addAll(hbox,new Label("Progress Bar:"), this.controller.getProgressBar(),this.helpButton);
	    
		return;
	}
	
	/**
	 * @brief D�finition des diff�rentes �venements (boutons)
	 */
	private void setActionButtons() {
		//ExecutorService permet de mettre des threads en file d'attente (ici � chaque clic) et les ex�cuter un par un:
		//On �vite ainsi bloquer le GUI avec les traitements qui peuvent �tre g�n�r� en parall�le lors de multi cliques:
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		
		if(this.ipButton != null) 
			this.ipButton.addEventHandler(ActionEvent.ACTION, event -> {
				//ipButton.setDisable(true); to avoid multi-clicks:
				ipHostname = ipField.getText();
				//on met notre thread en file:
				executorService.submit(new Runnable() {
				    public void run() {
				    	controller.generate(treeGraph.getView(),ipHostname);
				    }
				});
			});
		
		if(this.randomIpButton != null) {
			this.randomIpButton.addEventHandler(ActionEvent.ACTION, event -> {
				//ipButton.setDisable(true); to avoid multi-clicks:
				//this.randomIpButton.setDisable(true);
				if((ipHostname = this.controller.randomizeIPHostname(0,255)) != null) {
					//System.out.println(ipHostname); 
					executorService.submit(new Runnable() {
					    public void run() {
					    	controller.generate(treeGraph.getView(),ipHostname);
					    }
					});
				}
				//this.randomIpButton.setDisable(false);
			});
		}
		//Help Action:
		Label helpContent = new Label(HELP_TEXT);
		if(this.helpButton != null) {
			this.helpButton.addEventHandler(ActionEvent.ACTION, event -> {
				if(this.vBox.getChildren().contains(helpContent)) {
					this.vBox.getChildren().remove(helpContent);
					//Nous mettons ensuite notre progressBar en dernier �l�ment noeud graphique du parent (toBack() en premier �l�ment):
					//this.controller.getProgressBar().toFront();
				}
				else
					this.vBox.getChildren().add(helpContent);
					//this.controller.getProgressBar().toFront();
			});
		}
		
		return;
	}

	/**
	 * @brief Lancement de l'application (chargement et affichage de la fen�tre)
	 * @param fenetre : Fen�tre qui va �tre affich�e
	 * @throws Exception
	 */
	public void launch(Stage fenetre) throws Exception {
		StackPane root = new StackPane();
		
		this.setActionButtons();
		this.setControlViewBox(this.vBox);
		
		final Label label = new Label("Traceroute JavaFX - ADIB Ayoub / SARR Niébé");
		label.setFont(new Font("Calibri", 25));
		//CSS styles (here padding, we can use instead setLabelPadding() function)):
		label.setStyle("-fx-padding: 5px;");
		
		//Int�gration de graphstream dans javafx:
		sectionsWindow.setTop(label);
		sectionsWindow.setRight(this.vBox); 
		sectionsWindow.setCenter(treeGraph.setLayout()); 
		sectionsWindow.setBottom(treeList.setLayout());
		
		//Nous ajoutons tous nos �l�ments dans l'ordre dans notre super containeur root:
		root.getChildren().addAll(sectionsWindow);
		
		//on cr�e notre sc�ne:
		fenetre.setScene(new Scene(root,WIDTH,HEIGHT));
		//on ajoute un titre � notre fen�tre
		fenetre.setTitle("Traceroute JavaFX - ADIB Ayoub / SARR Niébé");
		//On set une taille minimale de fen�tre pour une bonne ergonomie:
		fenetre.setMinHeight(HEIGHT/1.2);
		fenetre.setMinWidth(WIDTH/1.2);
		//on affiche notre fen�tre:
		//fenetre.hide();
		fenetre.show();
	}
}
