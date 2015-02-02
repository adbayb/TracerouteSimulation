package view;

import model.NodeIP;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

//TreeView view:
//StackPane Layout pour TreeList:
public class TreeList {
	private StackPane stackPane;
	private TreeView<NodeIP> treeView;
	
	public TreeList(TreeItem<NodeIP> rootTree) {
		this.stackPane = new StackPane();
		this.treeView = new TreeView<NodeIP>(rootTree);
	}
	
	public StackPane getLayout() {
		
		return this.stackPane;
	}
	
	public StackPane setLayout() {
		this.stackPane.getChildren().add(this.treeView);
		//CSS:
		this.stackPane.setStyle("-fx-border-style: solid;"
		        + "-fx-border-color: black;"
		        + "-fx-border-width: 2;");
		//Section size (height:200):
		this.stackPane.setMaxHeight(200);
	    
		return this.stackPane;
	}
	
	public boolean changeLayout(StackPane pane) {
		if(pane != null) {
			this.stackPane = pane;
			
			return true;
		}
		
		return false;
	}
	
	public TreeView<NodeIP> getView() {
		return this.treeView;
	}
}
