package view;

import javafx.scene.layout.StackPane;

/**
 * 
 * @author SARR Niébé / ADIB Ayoub
 *
 */

public interface TreeViewInterface {
	/***
	 * @brief Récupration du panel
	 * @return le panel
	 */
	public StackPane getLayout();
	
	/**
	 * @brief Création du panel
	 * @return le panel modifié
	 */
	public StackPane setLayout();
	
	/**
	 * @brief Changement du contenu du panel
	 * @param pane
	 * @return le panel modifié
	 */
	public boolean changeLayout(StackPane pane);
}
