package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.Stage;
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

	/*public boolean addItem2Root(Ip ip) {
		return tree.addItem2Root(ip);
	}

	public boolean addSearchedItem(Ip ip, String namedNode) {
		return tree.addSearchedItem(ip,namedNode);
	}*/
	
	public boolean generate() {
		Processus processus = new Processus();
		
		BufferedReader reader = processus.execTraceroute("www.google.com");
		String line;	
	    String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"; 
		try {
			line = reader.readLine();			
			//TODO: http://stackoverflow.com/questions/10541157/sscanf-equivalent-in-java
			while(line != null) {
				System.out.println(line);
				String lineSplit[] = line.split(" ");
				List<String> lineWithoutSpace = new ArrayList<String>();
				for(int j = 0; j < lineSplit.length; j++){	
					//fakeroute
					if(lineSplit[j].matches(regex)){
						lineWithoutSpace.add(lineSplit[j]);
					}						
				}
				if(lineWithoutSpace.size() >= 1){
					//On génére notre arbre via addItems à partir de root (tree.getRootTree())
					tree.addItems(tree.getRootTree(),lineWithoutSpace);
				}
				line = reader.readLine();
			}						
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;		
	}
}
