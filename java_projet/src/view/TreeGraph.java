package view;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.StackPane;

//Graph view:
//StackPane Layout for TreeGraph:
public class TreeGraph {
	private StackPane stackPane;
	private Graph graph;
	//SwingNode
	private Viewer viewer;
	private SwingNode swingNode;
	
	public TreeGraph() {
		this.stackPane = new StackPane();
		this.graph = new SingleGraph("TreeGraph");
		//From API GraphStream: http://graphstream-project.org/api/gs-core/org/graphstream/ui/swingViewer/Viewer.html:
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
		//set GraphStream Nodes and Edges (defined in graph):
		//test:
		/*this.graph.addNode("A" );
	    this.graph.addNode("B" );
	    this.graph.addNode("C" );
	    //true permet d'afficher les flèches:
	    this.graph.addEdge("AB", "A", "B",true);
	    this.graph.addEdge("BC", "B", "C",true);
	    this.graph.addEdge("CA", "C", "A");*/
		
	    //Integrate graph to swing:
		this.viewer.enableAutoLayout();
		this.viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT);
		//permet d'inclure du contenu swing dans javafx:
		this.swingNode.setContent(this.viewer.addDefaultView(false));
		//swingNode.setContent(new JRadioButton("test"));
		//pane.setPrefWidth(WIDTH/3);
		this.stackPane.getChildren().add(swingNode);
	    
		return this.stackPane;
	}
	
	public boolean changeLayout(StackPane pane) {
		if(pane != null) {
			this.stackPane = pane;
			
			return true;
		}
		
		return false;
	}
	
	public Graph getView() {
		return this.graph;
	}
}
