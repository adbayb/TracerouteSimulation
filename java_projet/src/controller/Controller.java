package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import model.Ip;
import model.Processus;
import model.Tree;
import view.View;

/**
 * 
 * @author SARR Niébé / ADIB Ayoub
 *
 */
public class Controller {
	//dans controller on ajoute les modèles et vues associées! les modèles et vues 
	//ne doivent pas contenir de liens entre eux: tout passe par le controller qui les référencent!
	
	//Vue:
	private final View view;
	
	//Models:
	private final Tree tree;
	//private final Processus processus;

	public Controller(Tree tree, View view) {
		this.tree = tree;
		//this.processus = processus;
		this.view = view;
		view.setController(this);
	}

	public void start(Stage fenetre) throws Exception {
		view.launch(fenetre);
	}

	//DEBUG pour la vue:
	/*public boolean addItem(TreeItem<Ip> node, Ip ip) {
		return this.tree.addItem(node,ip);
	}
	
	public boolean addItem2Root(Ip ip) {
		return tree.addItem2Root(ip);
	}

	public boolean addSearchedItem(Ip ip, String namedNode) {
		return tree.addSearchedItem(ip,namedNode);
	}
	
	public TreeItem<Ip> getLastChild(TreeItem<Ip> node) {
		return this.tree.getLastChild(node);
	}
	
	public TreeItem<Ip> getParent(TreeItem<Ip> node) {
		return this.tree.getParent(node);
	}
	
	public TreeItem<Ip> search(TreeItem<Ip> currentNode, String value) {
		return this.tree.search(currentNode,value);
	}
	
	public TreeItem<Ip> getRoot() {
		return this.tree.getRoot();
	}
	
	public boolean editItem(TreeItem<Ip> node, Ip ip) {
		return this.tree.editItem(node,ip);
	}*/
	
	//On aurait pu mettre generate dans un Task pour réaliser les opération de fakeroute en arrière plan
	//Mais traceroute ne fait pas de long calcul (très rapide) donc inutile ici d'utilise un mode Concurrency
	//cf pour plus d'informations: http://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
	public boolean generate() {
		Processus processus = null;
		BufferedReader reader = null;
		String line = null;
		String regex = null;
		TreeItem<Ip> currentNode = null;
		int numberLine = 0;
		
		//On instancie notre classe processus et on lance le jar fakeroute en récupérant l'output dans reader:
		processus = new Processus();
		reader = processus.execTraceroute("google.fr");
		regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		//currentNode permet le noeud à partir duquel on ajoute les enfant au fur et à mesure que notre output fakeroute est lu:
		currentNode = this.tree.getRoot();
		try {
			line = reader.readLine();
			while(line != null) {
				System.out.println(line);
				
				String lineSplit[] = line.split(" ");
				List<String> lineWithoutSpace = new ArrayList<String>();
				for(int j = 0; j < lineSplit.length; j++){	
					//fakeroute
					//Si notre output line matche le regex définit:
					if(lineSplit[j].matches(regex)){
						lineWithoutSpace.add(lineSplit[j]);
					}
				}
				if(lineWithoutSpace.size() >= 1){
					//On génére notre arbre via addItems à partir de currentNode (débutant à Root)
					this.tree.addItems(currentNode,lineWithoutSpace,"Level "+numberLine+":");
					//Update currentNode:
					currentNode = this.tree.getLastChild(currentNode);
					//this.tree.addAllItems(this.tree.getRoot(),lineWithoutSpace,"");
				}
				numberLine++;
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;		
	}
}
