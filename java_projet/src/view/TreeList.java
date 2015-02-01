package view;

import model.Ip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

//TreeView view:
//StackPane Layout for TreeList:
public class TreeList {
	private StackPane stackPane;
	private TreeView<Ip> treeView;
	
	public TreeList(TreeItem<Ip> rootTree) {
		this.stackPane = new StackPane();
		this.treeView = new TreeView<Ip>(rootTree);
	}
	
	public StackPane getLayout() {
		
		return this.stackPane;
	}
	
	public StackPane setLayout() {
		this.stackPane.getChildren().add(this.treeView);
	    
		return this.stackPane;
	}
	
	public boolean changeLayout(StackPane pane) {
		if(pane != null) {
			this.stackPane = pane;
			
			return true;
		}
		
		return false;
	}
	
	public TreeView<Ip> getView() {
		return this.treeView;
	}
}
