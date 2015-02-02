package view;

import javafx.scene.layout.StackPane;

public interface TreeViewInterface {
	/***
	 * @brief R�cupration du panel
	 * @return le panel
	 */
	public StackPane getLayout();
	
	/**
	 * @brief Cr�ation du panel
	 * @return le panel modifi�
	 */
	public StackPane setLayout();
	
	/**
	 * @brief Changement du contenu du panel
	 * @param pane
	 * @return le panel modifi�
	 */
	public boolean changeLayout(StackPane pane);
}
