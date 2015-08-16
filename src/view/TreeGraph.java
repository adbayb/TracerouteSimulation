package view;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.StackPane;

/**
 * 
 * @author SARR Niébé / ADIB Ayoub
 *
 */

//Graph view:
//StackPane Layout for TreeGraph:
public class TreeGraph implements TreeViewInterface {
	private StackPane stackPane;
	private Graph graph;
	//SwingNode permettant d'inclure des composantes Swing dans JavaFX 
	private Viewer viewer;
	private SwingNode swingNode;
	
	public TreeGraph() {
		this.stackPane = new StackPane();
		this.graph = new SingleGraph("TreeGraph");
		//Source API GraphStream: http://graphstream-project.org/api/gs-core/org/graphstream/ui/swingViewer/Viewer.html:
		//New viewer on an existing graph. The viewer always run in the Swing thread, therefore, you must specify how it will take graph events from the graph you give. 
		//If the graph you give will be accessed only from the Swing thread use ThreadingModel.GRAPH_IN_SWING_THREAD. If the graph you use is accessed in another thread 
		//use ThreadingModel.GRAPH_IN_ANOTHER_THREAD. This last scheme is more powerful since it allows to run algorithms on the graph in parallel with the viewer.
		this.viewer = new Viewer(this.graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		this.swingNode = new SwingNode();
	}
	
	public StackPane getLayout() {
		
		return this.stackPane;
	}
	
	public StackPane setLayout() {
		//Intégration de GraphStream (utilisant Swing) dans un StackPane (javafx layout):
		this.viewer.enableAutoLayout();
		this.viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT);
		//permet d'inclure du contenu swing dans javafx:
		this.swingNode.setContent(this.viewer.addDefaultView(false));
		//swingNode.setContent(new JRadioButton("test"));
		this.stackPane.getChildren().add(swingNode);
		
		//Section size (max width available):
		this.stackPane.setMaxWidth(Double.MAX_VALUE);
		//this.stackPane.setMaxHeight(Double.MAX_VALUE);
		
		//CSS propriétés:
		this.stackPane.setStyle("-fx-border-style: solid;"
		        + "-fx-border-color: black;"
		        + "-fx-border-width: 2;");
	    
		return this.stackPane;
	}
	
	//Permet le changement du layout StackPane:
	public boolean changeLayout(StackPane pane) {
		if(pane != null) {
			this.stackPane = pane;
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * @brief Récupération de la Vue associé au graph arbre:
	 * @return
	 */
	public Graph getView() {
		return this.graph;
	}
}
