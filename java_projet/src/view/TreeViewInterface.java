package view;

import javafx.scene.layout.StackPane;

public interface TreeViewInterface {
	public StackPane getLayout();
	public StackPane setLayout();
	public boolean changeLayout(StackPane pane);
}
